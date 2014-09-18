/**
 * Developer: Kadvin Date: 14-9-17 下午3:54
 */
package dnt.itsnow.config;

import dnt.itsnow.repository.ItsnowProcessRepository;
import dnt.itsnow.service.ItsnowHostService;
import dnt.itsnow.service.ItsnowSchemaService;
import dnt.itsnow.support.ItsnowProcessManager;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Itsnow Process Manager Test Configuration
 */
@Configuration
@Import(BasicDeployConfig.class)
public class ItsnowProcessManagerConfig {
    //被测试对象
    @Bean
    public ItsnowProcessManager schemaManager(){
        return new ItsnowProcessManager();
    }


    //相关mock

    @Bean
    public ItsnowProcessRepository processRepository(){
        return EasyMock.createMock(ItsnowProcessRepository.class);
    }

    @Bean
    public ItsnowSchemaService schemaService(){
        return EasyMock.createMock(ItsnowSchemaService.class);
    }

    @Bean
    public ItsnowHostService hostService(){
        return EasyMock.createMock(ItsnowHostService.class);
    }
}
