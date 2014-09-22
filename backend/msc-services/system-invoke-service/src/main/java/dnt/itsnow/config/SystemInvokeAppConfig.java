/**
 * Developer: Kadvin Date: 14-9-18 上午10:40
 */
package dnt.itsnow.config;

import dnt.concurrent.StrategyExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

/**
 * <h1>系统调用模块的应用配置</h1>
 */
@Configuration
public class SystemInvokeAppConfig {

    @Bean(name = "systemInvokeExecutor")
    public ExecutorService systemInvokeExecutor(){
        return new StrategyExecutorService("system.invoke", "SystemInvocation");
    }
}
