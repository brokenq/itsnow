package dnt.itsnow.config;

import dnt.itsnow.service.MscRoleService;
import dnt.itsnow.support.MscRoleManager;
import dnt.messaging.MessageBus;
import dnt.messaging.support.DefaultMessageBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class MscRoleManagerConfig extends MscRoleRepositoryConfig {

    @Bean
    public MscRoleService mscRoleSerivce(){
        return new MscRoleManager();
    }

    @Bean
    public MessageBus globalMessageBus(){
        return new DefaultMessageBus();
    }

    @Bean
    public ExecutorService messagingPoolExecutor(){
        return Executors.newCachedThreadPool();
    }

    @Bean
    public TaskScheduler timeoutScheduler(){
        return new ThreadPoolTaskScheduler();
    }
}
