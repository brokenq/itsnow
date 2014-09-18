/**
 * Developer: Kadvin Date: 14-9-18 上午8:40
 */
package dnt.itsnow.config;

import dnt.itsnow.service.SystemInvokeService;
import dnt.itsnow.service.SystemInvocationTranslator;
import dnt.itsnow.support.SystemInvocationTranslation;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 最基础的发布相关的测试配置
 */
@Configuration
public class BasicDeployConfig {

    @Bean
    public SystemInvocationTranslator translator(){
        return new SystemInvocationTranslation();
    }

    @Bean
    public SystemInvokeService systemInvokeService(){
        return EasyMock.createMock(SystemInvokeService.class);
    }
}
