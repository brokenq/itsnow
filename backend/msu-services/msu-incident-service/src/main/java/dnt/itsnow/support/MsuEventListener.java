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

/**
 * Created by jacky on 2014/8/18.
 */
@Service
public class MsuEventListener extends Bean implements ActivitiEventListener, MessageListener {

    @Autowired
    MessageBus messageBus;

    @Autowired
    MsuIncidentRepository msuIncidentRepository;


    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        logger.debug("msu incident event:{},pid {}",new Object[]{activitiEvent.getType().toString(),activitiEvent.getProcessInstanceId()});
        ProcessDefinition processDefinition = activitiEvent.getEngineServices().getRepositoryService().createProcessDefinitionQuery().processDefinitionId(activitiEvent.getProcessDefinitionId()).singleResult();
        if(!processDefinition.getKey().equals(MsuIncidentManager.PROCESS_KEY))
            return;
        Task task = activitiEvent.getEngineServices().getTaskService().createTaskQuery().processInstanceId(activitiEvent.getProcessInstanceId()).singleResult();
        Incident incident = msuIncidentRepository.findByInstanceId(activitiEvent.getProcessInstanceId());

        if(task != null) {
            logger.debug("task id:{},name:{},desc:{},assignee:{},time:{}", task.getId(), task.getName(), task.getDescription(), task.getAssignee(), task.getCreateTime());

            if(task.getDescription().equals(IncidentStatus.Accepted.toString())) {
                this.processAcceptEvent(activitiEvent.getEngineServices(),task,incident);
            }else if(task.getDescription().equals(IncidentStatus.Resolving.toString())){
                this.processAnalysisEvent(activitiEvent.getEngineServices(), task, incident);
            }else if(task.getDescription().equals(IncidentStatus.Resolved.toString())) {
                this.processResolvedEvent(activitiEvent.getEngineServices(), task, incident);
            }else if(task.getDescription().equals(IncidentStatus.Closed.toString())) {
                this.processCloseEvent(activitiEvent.getEngineServices(),task,incident);
            }
            //send message to msp
            this.sendMessageToMsp(activitiEvent.getProcessInstanceId());
        }
    }

    private void processAcceptEvent(EngineServices engineServices,Task task,Incident incident){

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
        msuIncidentRepository.update(incident);
    }

    private void processAnalysisEvent(EngineServices engineServices,Task task,Incident incident){

        incident.setMsuStatus(IncidentStatus.Resolving);
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        List<IdentityLink> identityLinkList = engineServices.getTaskService().getIdentityLinksForTask(task.getId());
        for(IdentityLink link:identityLinkList){
            logger.debug("task assign type:{} user:{} group:{}",link.getType(),link.getUserId(),link.getGroupId());
            incident.setAssignedGroup(link.getGroupId());
        }
        engineServices.getTaskService().setAssignee(task.getId(), incident.getUpdatedBy());

        //update incident
        msuIncidentRepository.update(incident);
    }

    private void processResolvedEvent(EngineServices engineServices,Task task,Incident incident){
        incident.setMsuStatus(IncidentStatus.Resolved);
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setResolveTime(incident.getUpdatedAt());
        msuIncidentRepository.update(incident);
    }

    private void processCloseEvent(EngineServices engineServices,Task task,Incident incident){
        incident.setMsuStatus(IncidentStatus.Closed);
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setCloseTime(incident.getUpdatedAt());
        msuIncidentRepository.update(incident);
    }

    private void sendMessageToMsp(String instanceId){
        Incident incident = msuIncidentRepository.findByInstanceId(instanceId);
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

    @Override
    public void onMessage(String channel, String message) {
        logger.debug("receive channel:{}, string message:{}",channel,message);
        Incident incident = JsonSupport.parseJson(message,Incident.class);
        logger.debug("incident:"+incident.toString());
        this.updateIncident(incident);
    }

    private void updateIncident(Incident incident){
        msuIncidentRepository.update(incident);
    }

    @Override
    public void onMessage(String channel, byte[] message) {
        logger.debug("receive byte message:{}",message.toString());
    }
}
