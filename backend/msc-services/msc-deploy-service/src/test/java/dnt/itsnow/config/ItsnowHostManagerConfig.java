/**
 * Developer: Kadvin Date: 14-9-17 下午3:54
 */
package dnt.itsnow.config;

import dnt.itsnow.repository.ItsnowHostRepository;
import dnt.itsnow.service.SystemInvokeService;
import dnt.itsnow.support.ItsnowHostManager;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Itsnow Host Manager Test Configuration
 */
@Configuration
public class ItsnowHostManagerConfig {
    //被测试对象
    @Bean
    public ItsnowHostManager hostManager(){
        return new ItsnowHostManager();
    }

    @Bean
    public ItsnowHostRepository hostRepository(){
        return EasyMock.createMock(ItsnowHostRepository.class);
    }

    @Bean
    public SystemInvokeService systemInvokeService(){
        return EasyMock.createMock(SystemInvokeService.class);
    }
}
