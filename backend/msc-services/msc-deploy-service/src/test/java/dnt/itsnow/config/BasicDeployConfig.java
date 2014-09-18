/**
 * Developer: Kadvin Date: 14-9-18 上午8:40
 */
package dnt.itsnow.config;

import dnt.itsnow.service.SystemInvokeService;
import dnt.itsnow.service.SystemJobService;
import dnt.itsnow.support.SystemJobManager;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 最基础的发布相关的测试配置
 */
@Configuration
public class BasicDeployConfig {

    @Bean
    public SystemJobService jobService(){
        return new SystemJobManager();
    }

    @Bean
    public SystemInvokeService systemInvokeService(){
        return EasyMock.createMock(SystemInvokeService.class);
    }
}
