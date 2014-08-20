package dnt.itsnow.support;

import dnt.spring.Bean;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

/**
 * Created by jacky on 2014/8/18.
 */
@Service
public class GlobalEventListener extends Bean implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        logger.debug("触发了全局监听器, pid={}, tid={}, event={},description={}", new Object[]{
                delegateTask.getProcessInstanceId(), delegateTask.getId(), delegateTask.getEventName(),delegateTask.getDescription()
        });
    }
}
