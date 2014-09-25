/**
 * Developer: Kadvin Date: 14-9-23 下午2:12
 */
package dnt.itsnow.config;

import dnt.concurrent.StrategyExecutorService;
import dnt.itsnow.repository.ItsnowHostRepository;
import dnt.itsnow.repository.ItsnowProcessRepository;
import dnt.itsnow.repository.ItsnowSchemaRepository;
import dnt.itsnow.service.SystemInvocationTranslator;
import dnt.itsnow.service.SystemInvoker;
import dnt.itsnow.support.*;
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

    // 为Host Manager打得桩
    @Bean
    public ItsnowHostRepository hostRepository(){
        return EasyMock.createMock(ItsnowHostRepository.class);
    }

    // 被测试的入口(host manager)
    @Bean
    public ItsnowHostManager hostManager(){
        return new ItsnowHostManager();
    }


    // 为Schema Manager打得桩
    @Bean
    public ItsnowSchemaRepository schemaRepository(){
        return EasyMock.createMock(ItsnowSchemaRepository.class);
    }

    // 被测试的入口(schema manager)
    @Bean
    public ItsnowSchemaManager schemaManager(){
        return new ItsnowSchemaManager();
    }

    // 为Process Manager打得桩
    @Bean
    public ItsnowProcessRepository processRepository(){
        return EasyMock.createMock(ItsnowProcessRepository.class);
    }

    // 被测试的入口(process manager)
    @Bean
    public ItsnowProcessManager processManager(){
        return new ItsnowProcessManager();
    }


}
