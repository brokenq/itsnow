package dnt.itsnow.support;

import dnt.itsnow.repository.ActivitiSyncRepository;
import dnt.itsnow.service.ActivitiSyncService;
import dnt.messaging.MessageAdapter;
import dnt.messaging.MessageBus;
import dnt.spring.Bean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    @Qualifier("globalMessageBus")
    MessageBus globalMessageBus;

    ScheduledTaskRegistrar task;
    Executor               exe;

    @Autowired
    ActivitiSyncRepository activitiSyncRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    protected void performStart() {
        super.performStart();
        String[] channels = new String[]{"MscGroup", "MscUser", "MscRole"};
        globalMessageBus.subscribe(getClass().getName(), channels, new MessageAdapter() {
            @Override
            public void onMessage(String channel, String message) {
                if ("MscGroup".equals(channel)) {

                }
            }
        });
    }

    @Override
    public void syncActiviti() {
        //SELECT a.authority FROM group_authorities a union select b.authority from authorities b
        logger.info("begin sync activiti user and group");
        int size = jdbcTemplate.update("\n" +
                                       "DELETE FROM ACT_ID_MEMBERSHIP WHERE CONCAT(USER_ID_,GROUP_ID_) NOT IN (select CONCAT(username,authority) FROM itsnow_msc.authorities)");
        logger.info("delete ACT_ID_MEMBERSHIP size:{}", size);
        //activitiSyncRepository.deleteGroupMembers();
        size = jdbcTemplate.update("DELETE FROM ACT_ID_USER WHERE ID_ NOT IN (select username FROM itsnow_msc.users)");
        logger.info("delete ACT_ID_USER size:{}",size);
        //activitiSyncRepository.deleteUsers();
        size = jdbcTemplate.update("DELETE FROM ACT_ID_GROUP WHERE ID_ NOT IN (SELECT a.authority FROM group_authorities a union select b.authority from authorities b)");
        logger.info("delete ACT_ID_GROUP size:{}",size);
        //activitiSyncRepository.deleteGroups();
        size = jdbcTemplate.update("INSERT INTO ACT_ID_GROUP(ID_,NAME_,TYPE_) select c.authority,c.authority,'group' from (SELECT a.authority FROM itsnow_msc.group_authorities a union select b.authority from authorities b ) c WHERE c.authority NOT IN (SELECT ID_ FROM ACT_ID_GROUP)");
        logger.info("insert ACT_ID_GROUP size:{}",size);
        //activitiSyncRepository.insertGroups();
        size = jdbcTemplate.update("INSERT INTO ACT_ID_USER(ID_,EMAIL_) SELECT username,email FROM itsnow_msc.users WHERE username not in (select ID_ FROM ACT_ID_USER)");
        logger.info("insert ACT_ID_USER size:{}",size);
        //activitiSyncRepository.insertUsers();
        size = jdbcTemplate.update("INSERT INTO ACT_ID_MEMBERSHIP(USER_ID_,GROUP_ID_) SELECT username,authority FROM itsnow_msc.authorities WHERE CONCAT(username,authority) NOT IN (select CONCAT(USER_ID_,GROUP_ID_) FROM ACT_ID_MEMBERSHIP)");
        logger.info("insert ACT_ID_MEMBERSHIP size:{}",size);
        //activitiSyncRepository.insertGroupMembers();
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
