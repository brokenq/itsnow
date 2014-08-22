package dnt.itsnow.demo.support;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.demo.model.Incident;
import dnt.itsnow.demo.model.IncidentStatus;
import dnt.itsnow.demo.repository.MspIncidentRepository;
import dnt.messaging.MessageBus;
import dnt.messaging.MessageListener;
import dnt.spring.Bean;
import dnt.support.JsonSupport;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jacky on 2014/8/18.
 */
@Service
public class MspEventListener extends Bean implements ActivitiEventListener, MessageListener {

    @Autowired
    MessageBus messageBus;

    @Autowired
    MspIncidentRepository mspIncidentRepository;

    @Autowired
    ActivitiEngineService activitiEngineService;


    @Autowired
    MspIncidentManager mspIncidentManager;

    String accountName="DNT";
    String username = "jacky.cao";
    private static final String PROCESS_KEY = "msp_incident";


    public static final String CHANNEL="MSP-001-TO-MSU-001";

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        logger.debug("msp event:{},pid {}",new Object[]{activitiEvent.getType().toString(),activitiEvent.getProcessInstanceId()});
        //ProcessInstance processInstance = activitiEvent.getEngineServices().getRuntimeService().createProcessInstanceQuery().processInstanceId(activitiEvent.getProcessInstanceId()).singleResult();
        ProcessDefinition processDefinition = activitiEvent.getEngineServices().getRepositoryService().createProcessDefinitionQuery().processDefinitionId(activitiEvent.getProcessDefinitionId()).singleResult();
        if(!processDefinition.getKey().equals(MspIncidentManager.PROCESS_KEY))
            return;
        Task task = activitiEvent.getEngineServices().getTaskService().createTaskQuery().processInstanceId(activitiEvent.getProcessInstanceId()).singleResult();
        if(task != null) {
            logger.debug("task id:{},name:{},desc:{},assignee:{},time:{}", new Object[]{task.getId(), task.getName(), task.getDescription(), task.getAssignee(), task.getCreateTime()});
            //update incident status
            mspIncidentRepository.updateStatus(activitiEvent.getProcessInstanceId(),task.getDescription());
            //update response time / resolve time / close time
            if(task.getDescription().equals(IncidentStatus.Accepted.toString()))
                mspIncidentRepository.updateResponseTime(activitiEvent.getProcessInstanceId());
            else if(task.getDescription().equals(IncidentStatus.Resolved.toString()))
                mspIncidentRepository.updateResolveTime(activitiEvent.getProcessInstanceId());
            else if(task.getDescription().equals(IncidentStatus.Closed.toString()))
                mspIncidentRepository.updateCloseTime(activitiEvent.getProcessInstanceId());
            //send message to msu
            this.sendMessage(activitiEvent.getProcessInstanceId());
        }
    }

    private void sendMessage(String instanceId){
        Incident incident = mspIncidentRepository.findByInstanceId(instanceId);
        messageBus.publish(CHANNEL, JsonSupport.toJSONString(incident));
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
        this.saveOrUpdateIncident(incident);
    }

    private void saveOrUpdateIncident(Incident incident){
        long count = mspIncidentRepository.countByMsuAccountNameAndInstanceId(incident.getMsuAccountName(),incident.getMsuInstanceId());
        if(count > 0){
            //update incident
            mspIncidentRepository.updateByMsuAccountAndMsuInstanceId(incident);
        }else{
            //start msp process
            //start incident process
            ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(PROCESS_KEY, null, username);

            //save incident object and persist it
            incident.setCreatedBy(username);
            incident.setMspInstanceId(processInstance.getProcessInstanceId());
            incident.setMspAccountName(accountName);
            mspIncidentManager.newIncident(incident);
        }
    }

    @Override
    public void onMessage(String channel, byte[] message) {
        logger.debug("receive byte message:{}",message.toString());
    }
}
