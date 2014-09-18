/**
 * Developer: Kadvin Date: 14-9-17 下午3:54
 */
package dnt.itsnow.config;

import dnt.itsnow.repository.ItsnowHostRepository;
import dnt.itsnow.support.ItsnowHostManager;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Itsnow Host Manager Test Configuration
 */
@Configuration
@Import(BasicDeployConfig.class)
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
}
