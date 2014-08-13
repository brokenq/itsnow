package dnt.itsnow.support;

import dnt.itsnow.repository.ActivitiSyncRepository;
import dnt.itsnow.service.ActivitiSyncService;
import dnt.spring.Bean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by jacky on 2014/8/13.
 */
@Service
@EnableScheduling
public class ActivitiSyncManager extends Bean implements SchedulingConfigurer,DisposableBean,ActivitiSyncService {

    ScheduledTaskRegistrar task;
    Executor exe;

    @Autowired
    ActivitiSyncRepository activitiSyncRepository;

    @Override
    public void syncActiviti() {
        logger.info("begin sync activiti user and group");
        activitiSyncRepository.deleteGroupMembers();
        activitiSyncRepository.deleteUsers();
        activitiSyncRepository.deleteGroups();
        activitiSyncRepository.insertGroups();
        activitiSyncRepository.insertUsers();
        activitiSyncRepository.insertGroupMembers();
        logger.info("end sync activiti user and group");
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        task = taskRegistrar;
        taskRegistrar.setScheduler(taskScheduler());
        String cron = System.getProperty("activiti.sync.cron","5 * * * * *");
        taskRegistrar.addCronTask(new Runnable() {
            public void run() {
                syncActiviti();
            }
        },cron);

    }

    @org.springframework.context.annotation.Bean(destroyMethod="shutdown")
    public Executor taskScheduler() {
        exe = Executors.newScheduledThreadPool(10);
        return exe;
    }

    @Override
    public void destroy() throws Exception {
        if(this.task != null){
            task.destroy();
        }
    }
}
