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
 * Updated by stone on 2014/10/9.
 */
@Service
@EnableScheduling
public class ActivitiSyncManager extends Bean implements ActivitiSyncService {

    @Autowired
    @Qualifier("globalMessageBus")
    MessageBus globalMessageBus;

    @Autowired
    ActivitiSyncRepository activitiSyncRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    protected void performStart() {
        super.performStart();
        String[] channels = new String[]{"User", "Role"};
        globalMessageBus.subscribe(getClass().getName(), channels, new MessageAdapter() {
            @Override
            public void onMessage(String channel, String message) {

                String operator = message.substring(0,1);
                int size;

                logger.info("begin sync activiti user and role");

                if("User".equals(channel)){
                    if("+".equals(operator)){

                        size = jdbcTemplate.update("INSERT INTO ACT_ID_USER(ID_,EMAIL_) SELECT username,email FROM itsnow_msc.users WHERE username not in (select ID_ FROM ACT_ID_USER)");
                        logger.info("insert ACT_ID_USER size:{}",size);

                    }else if("-".equals(operator)){

                        size = jdbcTemplate.update("\n" +
                                "DELETE FROM ACT_ID_MEMBERSHIP WHERE CONCAT(USER_ID_,GROUP_ID_) NOT IN (select CONCAT(username,authority) FROM itsnow_msc.authorities)");
                        logger.info("delete ACT_ID_MEMBERSHIP size:{}",size);
                        //activitiSyncRepository.deleteGroupMembers();
                        size = jdbcTemplate.update("DELETE FROM ACT_ID_USER WHERE ID_ NOT IN (select username FROM itsnow_msc.users)");
                        logger.info("delete ACT_ID_USER size:{}",size);

                    }else if("*".equals(operator)){

                        size = jdbcTemplate.update("\n" +
                                "DELETE FROM ACT_ID_MEMBERSHIP WHERE CONCAT(USER_ID_,GROUP_ID_) NOT IN (select CONCAT(username,authority) FROM itsnow_msc.authorities)");
                        logger.info("delete ACT_ID_MEMBERSHIP size:{}",size);
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
                    }
                }else if("Role".equals(channel)){
                    if("+".equals(operator)){

                        //activitiSyncRepository.deleteGroups();
                        size = jdbcTemplate.update("INSERT INTO ACT_ID_GROUP(ID_,NAME_,TYPE_) select c.authority,c.authority,'group' from (SELECT a.authority FROM itsnow_msc.group_authorities a union select b.authority from authorities b ) c WHERE c.authority NOT IN (SELECT ID_ FROM ACT_ID_GROUP)");
                        logger.info("insert ACT_ID_GROUP size:{}",size);
                        size = jdbcTemplate.update("INSERT INTO ACT_ID_MEMBERSHIP(USER_ID_,GROUP_ID_) SELECT username,authority FROM itsnow_msc.authorities WHERE CONCAT(username,authority) NOT IN (select CONCAT(USER_ID_,GROUP_ID_) FROM ACT_ID_MEMBERSHIP)");
                        logger.info("insert ACT_ID_MEMBERSHIP size:{}",size);

                    }else if("-".equals(operator)){
                        size = jdbcTemplate.update("\n" +
                                "DELETE FROM ACT_ID_MEMBERSHIP WHERE CONCAT(USER_ID_,GROUP_ID_) NOT IN (select CONCAT(username,authority) FROM itsnow_msc.authorities)");
                        logger.info("delete ACT_ID_MEMBERSHIP size:{}",size);
                        size = jdbcTemplate.update("DELETE FROM ACT_ID_GROUP WHERE ID_ NOT IN (SELECT a.authority FROM group_authorities a union select b.authority from authorities b)");
                        logger.info("delete ACT_ID_GROUP size:{}",size);

                    }else if("*".equals(operator)){

                        size = jdbcTemplate.update("\n" +
                                "DELETE FROM ACT_ID_MEMBERSHIP WHERE CONCAT(USER_ID_,GROUP_ID_) NOT IN (select CONCAT(username,authority) FROM itsnow_msc.authorities)");
                        logger.info("delete ACT_ID_MEMBERSHIP size:{}",size);
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
                    }
                }

                logger.info("end sync activiti user and role");
            }
        });
    }

}
