package dnt.itsnow.support;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.model.Incident;
import dnt.itsnow.model.IncidentStatus;
import dnt.itsnow.repository.MspIncidentRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MspEventListener extends Bean implements ActivitiEventListener, MessageListener {

    @Autowired
    MessageBus messageBus;

    @Autowired
    MspIncidentRepository mspIncidentRepository;

    @Autowired
    ActivitiEngineService activitiEngineService;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Autowired
    MspIncidentManager mspIncidentManager;

    String accountName="DNT";
    String username = "jacky.cao";

    /**
     * 监听事件
     * @param activitiEvent
     */
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        logger.debug("msp incident event:{},pid {}", new Object[]{activitiEvent.getType().toString(), activitiEvent.getProcessInstanceId()});
        ProcessDefinition processDefinition = activitiEvent.getEngineServices().getRepositoryService().createProcessDefinitionQuery().processDefinitionId(activitiEvent.getProcessDefinitionId()).singleResult();
        if(!processDefinition.getKey().equals(MspIncidentManager.PROCESS_KEY))
            return;
        Task task = activitiEvent.getEngineServices().getTaskService().createTaskQuery().processInstanceId(activitiEvent.getProcessInstanceId()).singleResult();
        Incident incident = mspIncidentRepository.findByInstanceId(activitiEvent.getProcessInstanceId());

        if(task != null) {
            logger.debug("task id:{},name:{},desc:{},assignee:{},time:{}", task.getId(), task.getName(), task.getDescription(), task.getAssignee(), task.getCreateTime());

            if(task.getDescription().equals(IncidentStatus.Assigned.toString())) {
                this.processAssignedOrResolvedOrClosedEvent(incident,IncidentStatus.Assigned);
            }else if(task.getDescription().equals(IncidentStatus.Accepted.toString())) {
                this.processAcceptedOrAnalysisEvent(activitiEvent.getEngineServices(),task,incident,IncidentStatus.Accepted);
            }else if(task.getDescription().equals(IncidentStatus.Resolving.toString())){
                this.processAcceptedOrAnalysisEvent(activitiEvent.getEngineServices(), task, incident,IncidentStatus.Resolving);
            }else if(task.getDescription().equals(IncidentStatus.Resolved.toString())) {
                this.processAssignedOrResolvedOrClosedEvent(incident,IncidentStatus.Resolved);
            }else if(task.getDescription().equals(IncidentStatus.Closed.toString())) {
                this.processAssignedOrResolvedOrClosedEvent(incident,IncidentStatus.Closed);
            }
            //send message to msu
            this.sendMessageToMsu(activitiEvent.getProcessInstanceId());
        }
    }

    /**
     * 处理分配/解决/关闭事件
     * @param incident 故障表单
     * @param incidentStatus 故障状态
     */
    private void processAssignedOrResolvedOrClosedEvent(Incident incident,IncidentStatus incidentStatus){
        incident.setMspStatus(incidentStatus);
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if(incidentStatus == IncidentStatus.Resolved)
            incident.setResolveTime(incident.getUpdatedAt());
        else if(incidentStatus == IncidentStatus.Closed)
            incident.setCloseTime(incident.getUpdatedAt());
        mspIncidentRepository.update(incident);
    }

    /**
     * 处理签收/分析事件
     * @param engineServices 引擎服务
     * @param task 任务
     * @param incident 故障表单
     * @param incidentStatus 故障状态
     */
    private void processAcceptedOrAnalysisEvent(EngineServices engineServices,Task task,Incident incident,IncidentStatus incidentStatus){

        incident.setMspStatus(incidentStatus);
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setResponseTime(incident.getUpdatedAt());
        List<IdentityLink> identityLinkList = engineServices.getTaskService().getIdentityLinksForTask(task.getId());
        for(IdentityLink link:identityLinkList){
            logger.debug("task assign type:{} user:{} group:{}",link.getType(),link.getUserId(),link.getGroupId());
            incident.setAssignedGroup(link.getGroupId());
        }
        engineServices.getTaskService().setAssignee(task.getId(), incident.getUpdatedBy());

        //update incident
        mspIncidentRepository.update(incident);
    }

    /**
     * 发送消息至MSU
     * @param instanceId 流程实例ID
     */
    private void sendMessageToMsu(String instanceId){
        Incident incident = mspIncidentRepository.findByInstanceId(instanceId);
        messageBus.publish(MspIncidentManager.getSendChannel(), JsonSupport.toJSONString(incident));
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
     * 收到MSU发送过来的消息，新建或者更新故障表单
     * @param channel 消息通道
     * @param message 消息
     */
    @Override
    public void onMessage(String channel, String message) {
        logger.debug("receive channel:{}, string message:{}",channel,message);
        Incident incident = JsonSupport.parseJson(message,Incident.class);
        logger.debug("incident:"+incident.toString());
        this.saveOrUpdateIncident(incident);
    }

    /**
     * 根据MSU的故障表单启动故障流程或者更新故障表单
     * @param incident
     */
    private void saveOrUpdateIncident(Incident incident){
        long count = mspIncidentRepository.countByMsuAccountNameAndInstanceId(incident.getMsuAccountName(),incident.getMsuInstanceId());
        if(count > 0){
            //update incident
            mspIncidentRepository.updateByMsuAccountAndMsuInstanceId(incident);
        }else{
            //start msp process
            //start incident process
            ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(MspIncidentManager.PROCESS_KEY, null, username);

            //save incident object and persist it
            incident.setCreatedBy(username);
            incident.setMspInstanceId(processInstance.getProcessInstanceId());
            incident.setMspAccountName(accountName);
            incident.setNumber("INC" + df.format(new Date()));
            incident.setMspStatus(IncidentStatus.New);
            incident.setUpdatedBy(username);
            incident.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            incident.setUpdatedAt(incident.getCreatedAt());
            mspIncidentRepository.create(incident);
        }
    }

    @Override
    public void onMessage(String channel, byte[] message) {
        logger.debug("receive byte message:{}",message.length);
    }
}
