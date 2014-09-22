package dnt.itsnow.support;

import dnt.itsnow.model.Incident;
import dnt.itsnow.model.IncidentStatus;
import dnt.itsnow.repository.MsuIncidentRepository;
import dnt.messaging.MessageBus;
import dnt.messaging.MessageListener;
import dnt.spring.Bean;
import dnt.support.JsonSupport;
import org.activiti.engine.EngineServices;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MsuEventListener extends Bean implements ActivitiEventListener, MessageListener {

    @Autowired
    MessageBus messageBus;

    @Autowired
    MsuIncidentRepository repository;

    /**
     * <h2>接收Activiti流程事件，根据故障流程中不同的状态执行不同的操作</h2>
     * @param activitiEvent 事件
     */
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        logger.debug("msu incident event:{},pid {}",new Object[]{activitiEvent.getType().toString(),activitiEvent.getProcessInstanceId()});
        ProcessDefinition processDefinition = activitiEvent.getEngineServices().getRepositoryService().createProcessDefinitionQuery().processDefinitionId(activitiEvent.getProcessDefinitionId()).singleResult();
        if(!processDefinition.getKey().equals(MsuIncidentManager.PROCESS_KEY))
            return;
        Task task = activitiEvent.getEngineServices().getTaskService().createTaskQuery().processInstanceId(activitiEvent.getProcessInstanceId()).singleResult();
        Incident incident = repository.findByInstanceId(activitiEvent.getProcessInstanceId());

        if(task != null) {
            logger.debug("task id:{},name:{},desc:{},assignee:{},time:{}", task.getId(), task.getName(), task.getDescription(), task.getAssignee(), task.getCreateTime());

            if(task.getDescription().equals(IncidentStatus.Accepted.toString())) {
                this.processAcceptedEvent(activitiEvent.getEngineServices(),task,incident);
            }else if(task.getDescription().equals(IncidentStatus.Resolving.toString())){
                this.processResolvingEvent(activitiEvent.getEngineServices(), task, incident);
            }else if(task.getDescription().equals(IncidentStatus.Resolved.toString())) {
                this.processResolvedEvent(incident);
            }else if(task.getDescription().equals(IncidentStatus.Closed.toString())) {
                this.processClosedEvent(incident);
            }
            //send message to msp
            this.sendMessageToMsp(activitiEvent.getProcessInstanceId());
        }
    }

    /**
     * <h2>处理签收事件</h2>
     *
     * @param engineServices  流程引擎服务
     * @param task  流程任务
     * @param incident 故障表单
     */
    private void processAcceptedEvent(EngineServices engineServices,Task task,Incident incident){

        incident.setMsuStatus(IncidentStatus.Accepted);
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setResponseTime(incident.getUpdatedAt());
        List<IdentityLink> identityLinkList = engineServices.getTaskService().getIdentityLinksForTask(task.getId());
        for(IdentityLink link:identityLinkList){
            logger.debug("task assign type:{} user:{} group:{}",link.getType(),link.getUserId(),link.getGroupId());
            incident.setAssignedGroup(link.getGroupId());
        }
        engineServices.getTaskService().setAssignee(task.getId(), incident.getUpdatedBy());
        //update incident
        repository.update(incident);
    }

    /**
     * <h2>处理分析事件</h2>
     *
     * @param engineServices  流程引擎服务
     * @param task  流程任务
     * @param incident 故障表单
     */
    private void processResolvingEvent(EngineServices engineServices,Task task,Incident incident){

        if(incident.getAssignedGroup()!=null && incident.getAssignedGroup().equals(MsuIncidentManager.ROLE_LINE_ONE)){
            if(incident.getCanProcess() != null && incident.getCanProcess() == true){
                incident.setMsuStatus(IncidentStatus.Resolving);
                engineServices.getTaskService().setAssignee(task.getId(), incident.getUpdatedBy());
            }
            else
                incident.setMsuStatus(IncidentStatus.Assigned);
        }
        else if(incident.getAssignedGroup()!=null && incident.getAssignedGroup().equals(MsuIncidentManager.ROLE_LINE_TWO)){
            incident.setMsuStatus(IncidentStatus.Resolving);
        }
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setResponseTime(incident.getUpdatedAt());

        //update incident
        repository.update(incident);
    }

    /**
     * <h2>处理解决事件</h2>
     *
     * @param incident 故障表单
     */
    private void processResolvedEvent(Incident incident ){

        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if(incident.getResolved()!=null && incident.getResolved() == true){
            incident.setMsuStatus(IncidentStatus.Resolved);
            incident.setResolveTime(incident.getUpdatedAt());
        }
        else{
            incident.setMsuStatus(IncidentStatus.Assigned);
        }
        repository.update(incident);
    }

    /**
     * <h2>处理关闭事件</h2>
     *
     * @param incident 故障表单
     */
    private void processClosedEvent(Incident incident ){
        incident.setMsuStatus(IncidentStatus.Closed);
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setCloseTime(incident.getUpdatedAt());
        repository.update(incident);
    }

    /**
     * <h2>发送消息至MSP</h2>
     * @param instanceId 流程实例ID
     */
    private void sendMessageToMsp(String instanceId){
        Incident incident = repository.findByInstanceId(instanceId);
        messageBus.publish(MsuIncidentManager.getSendChannel(), JsonSupport.toJSONString(incident));
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    /**
     * <h2>接收到MSP发送的消息，更新故障信息</h2>
     * @param channel 通道
     * @param message 消息JSON字符串
     */
    @Override
    public void onMessage(String channel, String message) {
        logger.debug("receive channel:{}, string message:{}",channel,message);
        Incident incident = JsonSupport.parseJson(message,Incident.class);
        logger.debug("incident:"+incident.toString());
        this.updateIncident(incident);
    }

    /**
     * <h2>更新故障信息</h2>
     * @param incident 故障表单数据
     */
    private void updateIncident(Incident incident){
        repository.update(incident);
    }

    @Override
    public void onMessage(String channel, byte[] message) {
        logger.debug("receive byte message:{}",message.length);
    }
}
