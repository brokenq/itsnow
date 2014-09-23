/**
 * Developer: Kadvin Date: 14-9-23 下午2:12
 */
package dnt.itsnow.config;

import dnt.concurrent.StrategyExecutorService;
import dnt.itsnow.repository.ItsnowHostRepository;
import dnt.itsnow.service.SystemInvocationTranslator;
import dnt.itsnow.service.SystemInvokeService;
import dnt.itsnow.service.SystemInvoker;
import dnt.itsnow.support.DefaultSystemInvoker;
import dnt.itsnow.support.ItsnowHostManager;
import dnt.itsnow.support.SystemInvocationTranslation;
import dnt.itsnow.support.SystemInvokeManager;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ExecutorService;


/**
 * 基于实际脚本调用环境的发布相关的测试配置
 */
@Configuration
public class DeployScriptConfig {
    // System Invoke Service 用到的对象
    @Bean(name = "systemInvokeExecutor")
    public ExecutorService systemInvokeExecutor(){
        return new StrategyExecutorService("system.invoke", "SystemInvocation");
    }

    @Bean
    public SystemInvoker systemInvoker(){
        return new DefaultSystemInvoker();
    }

    @Bean
    public TaskScheduler cleanScheduler(){
        return new ThreadPoolTaskScheduler();
    }

    // System Invoke 对象
    @Bean
    public SystemInvokeManager systemInvokeService(){
        return new SystemInvokeManager();
    }

    // Manager需要用到的对象
    @Bean
    public SystemInvocationTranslator translator(){
        return new SystemInvocationTranslation();
    }

    // 为Manager打得桩
    @Bean
    public ItsnowHostRepository hostRepository(){
        return EasyMock.createMock(ItsnowHostRepository.class);
    }

    // 被测试的入口(manager)
    @Bean
    public ItsnowHostManager hostManager(){
        return new ItsnowHostManager();
    }


}
