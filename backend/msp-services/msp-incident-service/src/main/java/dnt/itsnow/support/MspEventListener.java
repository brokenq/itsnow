package dnt.itsnow.support;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.model.*;
import dnt.itsnow.repository.MspIncidentRepository;
import dnt.itsnow.service.CommonAccountService;
import dnt.messaging.MessageBus;
import dnt.messaging.MessageListener;
import dnt.spring.Bean;
import dnt.support.JsonSupport;
import org.activiti.engine.EngineServices;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MspEventListener extends Bean implements ActivitiEventListener, MessageListener {

    @Autowired
    @Qualifier("globalMessageBus")
    MessageBus messageBus;

    @Autowired
    MspIncidentRepository mspIncidentRepository;

    @Autowired
    ActivitiEngineService activitiEngineService;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Autowired
    MspIncidentManager mspIncidentManager;

    @Autowired
    CommonAccountService accountService;

    String username = "msp_admin";

    Map<String, String> channelMap = new HashMap<String, String>();

    static Long count = 1L;

    /**
     * 监听事件
     *
     * @param activitiEvent
     */
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        logger.debug("receiving msp incident event:{},pid {}",
                new Object[]{activitiEvent.getType().toString(), activitiEvent.getProcessInstanceId()});
        ProcessDefinition processDefinition =
                activitiEvent.getEngineServices().getRepositoryService().createProcessDefinitionQuery()
                        .processDefinitionId(activitiEvent.getProcessDefinitionId()).singleResult();
        if (!processDefinition.getKey().equals(MspIncidentManager.PROCESS_KEY))
            return;
        Task task = activitiEvent.getEngineServices().getTaskService().createTaskQuery()
                .processInstanceId(activitiEvent.getProcessInstanceId()).singleResult();
        Incident incident = mspIncidentRepository.findByInstanceId(activitiEvent.getProcessInstanceId());

        if (task != null) {
            logger.debug("task id:{},name:{},desc:{},assignee:{},time:{}", task.getId(), task.getName(),
                    task.getDescription(), task.getAssignee(), task.getCreateTime());
            if (task.getDescription().equals(IncidentStatus.Assigned.toString())) {
                this.processAssignedOrResolvedOrClosedEvent(incident, IncidentStatus.Assigned);
            } else if (task.getDescription().equals(IncidentStatus.Accepted.toString())) {
                this.processAcceptedOrAnalysisEvent(activitiEvent.getEngineServices(), task, incident, IncidentStatus.Accepted);
            } else if (task.getDescription().equals(IncidentStatus.Resolving.toString())) {
                this.processAcceptedOrAnalysisEvent(activitiEvent.getEngineServices(), task, incident, IncidentStatus.Resolving);
            } else if (task.getDescription().equals(IncidentStatus.Resolved.toString())) {
                this.processAssignedOrResolvedOrClosedEvent(incident, IncidentStatus.Resolved);
            } else if (task.getDescription().equals(IncidentStatus.Closed.toString())) {
                this.processAssignedOrResolvedOrClosedEvent(incident, IncidentStatus.Closed);
            }
            //send message to msu
            this.sendMessageToMsu(activitiEvent.getProcessInstanceId(), TransferType.View);
        }
        logger.debug("received msp incident event");
    }

    /**
     * 处理分配/解决/关闭事件
     *
     * @param incident       故障表单
     * @param incidentStatus 故障状态
     */
    private void processAssignedOrResolvedOrClosedEvent(Incident incident, IncidentStatus incidentStatus) {
        logger.debug("processing {} event", incidentStatus);
        incident.setMspStatus(incidentStatus);
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if (incidentStatus == IncidentStatus.Resolved) {
            incident.setResolveTime(incident.getUpdatedAt());
            incident.setAssignedUser(null);
        } else if (incidentStatus == IncidentStatus.Closed)
            incident.setCloseTime(incident.getUpdatedAt());
        mspIncidentRepository.update(incident);
        logger.debug("processed {} event", incidentStatus);
    }

    /**
     * 处理签收/分析事件
     *
     * @param engineServices 引擎服务
     * @param task           任务
     * @param incident       故障表单
     * @param incidentStatus 故障状态
     */
    private void processAcceptedOrAnalysisEvent(EngineServices engineServices, Task task, Incident incident, IncidentStatus incidentStatus) {
        logger.debug("processing {} event", incidentStatus);
        incident.setMspStatus(incidentStatus);
        incident.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setResponseTime(incident.getUpdatedAt());
        incident.setAssignedUser(incident.getUpdatedBy());
        engineServices.getTaskService().setAssignee(task.getId(), incident.getUpdatedBy());
        //update incident
        mspIncidentRepository.update(incident);
        logger.debug("processed {} event", incidentStatus);
    }

    /**
     * 发送消息至MSU
     *
     * @param instanceId 流程实例ID
     */
    private void sendMessageToMsu(String instanceId, TransferType type) {
        logger.debug("sending message to msu , instance:{}", instanceId);
        Incident incident = mspIncidentRepository.findByInstanceId(instanceId);
        String sendChannel = channelMap.get(incident.getMsuAccountName());
        if (sendChannel != null) {
            TransferObject obj = new TransferObject();
            obj.setId(count++);
            obj.setCreated(new Timestamp(System.currentTimeMillis()));
            obj.setContentType(ContentType.Incident);
            obj.setFrom(incident.getMspAccountName());
            obj.setTo(incident.getMsuAccountName());
            obj.setType(type);
            obj.setChannel(MspIncidentManager.getListenChannel());
            obj.setContent(JsonSupport.toJSONString(incident));
            messageBus.publish(sendChannel, JsonSupport.toJSONString(obj));
            logger.debug("sent message to msu channel:{}", sendChannel);
        } else {
            logger.warn("can't find send channel!");
        }
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
     *
     * @param channel 消息通道
     * @param message 消息
     */
    @Override
    public void onMessage(String channel, String message) {
        logger.debug("receiving channel:{}, string message:{}", channel, message);
        TransferObject obj = JsonSupport.parseJson(message, TransferObject.class);
        if (obj.getContentType() == ContentType.Incident) {
            //add channel to map
            if (!channelMap.containsKey(obj.getFrom()))
                channelMap.put(obj.getFrom(), obj.getChannel());
            Incident incident = JsonSupport.parseJson(obj.getContent(), Incident.class);
            logger.debug("incident:" + incident.toString());

            this.saveOrUpdateIncident(incident, obj.getType());

        }

    }

    /**
     * 根据MSU的故障表单启动故障流程或者更新故障表单
     *
     * @param incident
     */
    private void saveOrUpdateIncident(Incident incident, TransferType type) {
        logger.info("TransferType:{} incident:{}", type, incident.getMsuInstanceId());

        try {
            long count = mspIncidentRepository.countByMsuAccountNameAndInstanceId(incident.getMsuAccountName(), incident.getMsuInstanceId());
            if (count > 0) {
                //update incident
                if( type == TransferType.View)
                    mspIncidentRepository.updateMsuStatusByMsuAccountAndMsuInstanceId(incident);
                else if(type == TransferType.Action){
                    String mspInstanceId = mspIncidentRepository.findMspInstanceIdByMsuAccountNameAndInstanceId(incident.getMsuAccountName(),incident.getMsuInstanceId());
                    if(mspInstanceId == null || mspInstanceId == "") {
                        startMspInstance(incident);
                        mspIncidentRepository.updateMspStatusByMsuAccountAndMsuInstanceId(incident);
                    }
                }
                logger.info("update incident:{}", incident.getMsuInstanceId());
            } else {
                if (type == TransferType.Action) {
                    startMspInstance(incident);
                }
                mspIncidentRepository.create(incident);
                logger.info("save {} msu incident :{}", type, incident.getMsuInstanceId());
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
        }


    }

    private void startMspInstance(Incident incident){
        //start msp process
        //start incident process
        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(MspIncidentManager.PROCESS_KEY, null, username);
        logger.info("started msp incident workflow,instance:{}", processInstance.getProcessInstanceId());
        //save incident object and persist it
        incident.setCreatedBy(username);
        incident.setMspInstanceId(processInstance.getProcessInstanceId());
        Account account = accountService.findBySn(MspIncidentManager.getAppSn());
        incident.setMspAccountName(account.getName());
        incident.setNumber("INC" + df.format(new Date()));
        incident.setMspStatus(IncidentStatus.New);
        incident.setUpdatedBy(username);
        incident.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setUpdatedAt(incident.getCreatedAt());
        incident.setAssignedUser(null);
        incident.setAssignedGroup(null);
        incident.setCloseTime(null);
        incident.setResolveTime(null);
        incident.setResponseTime(null);
        incident.setCanProcess(null);
        incident.setHardwareError(null);
        incident.setResolved(null);
    }

    @Override
    public void onMessage(String channel, byte[] message) {
        logger.debug("receive byte message:{}", message.length);
    }
}
