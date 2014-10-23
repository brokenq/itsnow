package dnt.itsnow.config;

import dnt.itsnow.service.CommonServiceCatalogService;
import dnt.itsnow.support.CommonServiceCatalogManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>CommonServiceCatalogManagerConfig</h1>
 */
@Configuration
public class CommonServiceCatalogManagerConfig extends CommonServiceCatalogRepositoryConfig {

    @Bean
    public CommonServiceCatalogService commonServiceCatalogService(){
        return new CommonServiceCatalogManager();
    }
}
