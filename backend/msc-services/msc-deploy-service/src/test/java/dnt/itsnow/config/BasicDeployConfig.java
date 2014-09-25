/**
 * Developer: Kadvin Date: 14-9-18 上午8:40
 */
package dnt.itsnow.config;

import dnt.itsnow.listener.SystemInvocationListener;
import dnt.itsnow.service.SystemInvokeService;
import dnt.itsnow.service.SystemInvocationTranslator;
import dnt.itsnow.support.SystemInvocationTranslation;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;

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
        SystemInvokeService invokeService = EasyMock.createMock(SystemInvokeService.class);
        invokeService.addListener(isA(SystemInvocationListener.class));
        expectLastCall().anyTimes();
        invokeService.removeListener(isA(SystemInvocationListener.class));
        expectLastCall().anyTimes();
        return invokeService;
    }
}
