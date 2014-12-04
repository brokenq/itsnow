/**
 * Developer: Kadvin Date: 14-9-18 上午10:40
 */
package dnt.itsnow.config;

import net.happyonroad.concurrent.StrategyExecutorService;
import net.happyonroad.platform.config.DefaultAppConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;

/**
 * <h1>系统调用模块的应用配置</h1>
 */
@Configuration
@Import(DefaultAppConfig.class)
public class SystemInvokeAppConfig {

    @Bean(name = "systemInvokeExecutor")
    public ExecutorService systemInvokeExecutor(){
        return new StrategyExecutorService("system.invoke", "SystemInvocation");
    }
}
