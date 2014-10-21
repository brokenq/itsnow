package dnt.itsnow.config;

import dnt.itsnow.service.RoleService;
import dnt.itsnow.support.RoleManager;
import dnt.messaging.MessageBus;
import dnt.messaging.support.DefaultMessageBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>角色管理测试类配置</h1>
 */
@Configuration
public class RoleManagerConfig extends RoleRepositoryConfig {

    @Bean
    public RoleService roleSerivce(){
        return new RoleManager();
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
