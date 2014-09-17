/**
 * Developer: Kadvin Date: 14-9-17 下午3:54
 */
package dnt.itsnow.config;

import dnt.itsnow.repository.ItsnowSchemaRepository;
import dnt.itsnow.service.SystemInvokeService;
import dnt.itsnow.support.ItsnowSchemaManager;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Itsnow Schema Manager Test Configuration
 */
@Configuration
public class ItsnowSchemaManagerConfig {
    //被测试对象
    @Bean
    public ItsnowSchemaManager schemaManager(){
        return new ItsnowSchemaManager();
    }

    //相关mock

    @Bean
    public ItsnowSchemaRepository schemaRepository(){
        return EasyMock.createMock(ItsnowSchemaRepository.class);
    }

    @Bean
    public SystemInvokeService systemInvokeService(){
        return EasyMock.createMock(SystemInvokeService.class);
    }
}
