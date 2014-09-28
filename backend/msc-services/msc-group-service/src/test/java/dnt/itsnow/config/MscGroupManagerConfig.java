package dnt.itsnow.config;

import dnt.itsnow.service.MscGroupService;
import dnt.itsnow.support.MscGroupManager;
import dnt.messaging.MessageBus;
import dnt.messaging.support.DefaultMessageBus;
import org.easymock.EasyMock;
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
public class MscGroupManagerConfig extends MscGroupRepositoryConfig {

    @Bean
    public MscGroupService mscGroupService(){
        return new MscGroupManager();
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
