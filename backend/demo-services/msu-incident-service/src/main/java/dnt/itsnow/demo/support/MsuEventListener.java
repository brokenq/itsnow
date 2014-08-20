package dnt.itsnow.demo.support;

import dnt.itsnow.demo.repository.MsuIncidentRepository;
import dnt.messaging.MessageBus;
import dnt.messaging.MessageListener;
import dnt.spring.Bean;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jacky on 2014/8/18.
 */
@Service
public class MsuEventListener extends Bean implements ActivitiEventListener, MessageListener {

    @Autowired
    MessageBus messageBus;

    @Autowired
    MsuIncidentRepository msuIncidentRepository;

    public static final String CHANNEL="MSU-001-TO-MSP-001";

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        logger.debug("msu event:{},pid {}",new Object[]{activitiEvent.getType().toString(),activitiEvent.getProcessInstanceId()});
        //ProcessInstance processInstance = activitiEvent.getEngineServices().getRuntimeService().createProcessInstanceQuery().processInstanceId(activitiEvent.getProcessInstanceId()).singleResult();
        Task task = activitiEvent.getEngineServices().getTaskService().createTaskQuery().processInstanceId(activitiEvent.getProcessInstanceId()).singleResult();
        if(task != null) {
            logger.debug("task id:{},name:{},desc:{},assignee:{},time:{}", new Object[]{task.getId(), task.getName(), task.getDescription(), task.getAssignee(), task.getCreateTime()});
            //update incident status
            msuIncidentRepository.updateStatus(activitiEvent.getProcessInstanceId(),task.getDescription());
            messageBus.publish(CHANNEL, task.toString());
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

    @Override
    public void onMessage(String channel, String message) {
        logger.debug("receive channel:{}, string message:{}",channel,message);
    }

    @Override
    public void onMessage(String channel, byte[] message) {
        logger.debug("receive byte message:{}",message.toString());
    }
}
