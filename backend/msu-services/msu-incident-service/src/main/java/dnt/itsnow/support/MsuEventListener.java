package dnt.itsnow.support;

import dnt.itsnow.api.ActivitiEngineService;
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
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MsuEventListener extends Bean implements ActivitiEventListener, MessageListener {

    @Autowired
    MessageBus messageBus;

    @Autowired
    MsuIncidentRepository repository;

    @Autowired
    ActivitiEngineService activitiEngineService;

    /**
     * <h2>接收Activiti流程事件，根据故障流程中不同的状态执行不同的操作</h2>
     * @param activitiEvent 事件
     */
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        logger.debug("receiving msu incident event:{},pid {}",new Object[]{activitiEvent.getType().toString(),activitiEvent.getProcessInstanceId()});
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
        }
        logger.debug("received msu incident event");
    }

    /**
     * <h2>处理签收事件</h2>
     *
     * @param engineServices  流程引擎服务
     * @param task  流程任务
     * @param incident 故障表单
     */
    private void processAcceptedEvent(EngineServices engineServices,Task task,Incident incident){
        logger.debug("processing accepted event,instance:{}",incident.getMsuInstanceId());
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
        logger.debug("processed accepted event");
    }

    /**
     * <h2>处理分析事件</h2>
     *
     * @param engineServices  流程引擎服务
     * @param task  流程任务
     * @param incident 故障表单
     */
    private void processResolvingEvent(EngineServices engineServices,Task task,Incident incident){
        logger.debug("process resolving event,instance:{}",incident.getMsuInstanceId());
        if(incident.getAssignedGroup()!=null && incident.getAssignedGroup().equals(MsuIncidentManager.ROLE_LINE_ONE)){
            if(incident.getCanProcess() != null && incident.getCanProcess()){
                incident.setMsuStatus(IncidentStatus.Resolving);
                engineServices.getTaskService().setAssignee(task.getId(), incident.getUpdatedBy());
            }
            else
                incident.setMsuStatus(IncidentStatus.Assigned);
        }
        else if(incident.getAssignedGroup()!=null && incident.getAssignedGroup().equals(MsuIncidentManager.ROLE_LINE_TWO)){
            incident.setMsuStatus(IncidentStatus.Resolving);
            if(incident.getHardwareError() != null && incident.getHardwareError()){
                //send message to msp
                this.sendMessageToMsp(task.getProcessInstanceId());
            }
        }
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setResponseTime(incident.getUpdatedAt());

        //update incident
        repository.update(incident);
        logger.debug("processed resolving event");
    }

    /**
     * <h2>处理解决事件</h2>
     *
     * @param incident 故障表单
     */
    private void processResolvedEvent(Incident incident ){
        logger.debug("processing resolved event,instance:{}",incident.getMsuInstanceId());
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if(incident.getResolved()!=null && incident.getResolved()){
            incident.setMsuStatus(IncidentStatus.Resolved);
            incident.setResolveTime(incident.getUpdatedAt());
        }
        else{
            incident.setMsuStatus(IncidentStatus.Assigned);
        }
        repository.update(incident);
        logger.debug("processed resolved event");
    }

    /**
     * <h2>处理关闭事件</h2>
     *
     * @param incident 故障表单
     */
    private void processClosedEvent(Incident incident ){
        logger.debug("processing closed event,instance:{}",incident.getMsuInstanceId());
        incident.setMsuStatus(IncidentStatus.Closed);
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setCloseTime(incident.getUpdatedAt());
        repository.update(incident);
        logger.debug("processed closed event");
    }

    /**
     * <h2>发送消息至MSP</h2>
     * @param instanceId 流程实例ID
     */
    private void sendMessageToMsp(String instanceId){
        logger.debug("sending message to msp,instance:{}",instanceId);
        Incident incident = repository.findByInstanceId(instanceId);
        messageBus.publish(MsuIncidentManager.getSendChannel(), JsonSupport.toJSONString(incident));
        logger.debug("sent message to msp channel:{}",MsuIncidentManager.getSendChannel());
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
        logger.debug("receiving channel:{}, string message:{}",channel,message);
        Incident incident = JsonSupport.parseJson(message,Incident.class);
        logger.debug("incident:"+incident.toString());
        this.updateIncident(incident);
        if(incident.getMspStatus()==IncidentStatus.Closed){
            this.finishMspTask(incident);
        }
        logger.debug("received message and finished");
    }

    /**
     * 当MSP的流程状态为closed时，自动完成MSP的任务，从而可以转到关闭的任务
     * @param incident 故障单信息
     */
    private void finishMspTask(Incident incident) {
        logger.debug("receive msp closed incident:{}",incident.getMsuInstanceId());
        ProcessInstance instance = activitiEngineService.queryProcessInstanceById(incident.getMsuInstanceId());
        String activitiId = instance.getActivityId();
        if(activitiId.equals(MsuIncidentManager.SECONDLINE_CALL_MSP)) {//prevent duplicate event
            Task currTask = activitiEngineService.queryTask(incident.getMsuInstanceId(), activitiId);
            Map<String, String> taskVariables = new HashMap<String, String>();
            activitiEngineService.completeTask(currTask.getId(), taskVariables, "steve.li");
            logger.debug("Msu Incident auto complete task:{}",currTask.getId());
        }

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
