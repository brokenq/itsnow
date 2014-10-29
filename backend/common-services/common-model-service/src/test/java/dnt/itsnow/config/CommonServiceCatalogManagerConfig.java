package dnt.itsnow.config;

import dnt.itsnow.service.CommonServiceCatalogService;
import dnt.itsnow.service.CommonServiceItemService;
import dnt.itsnow.support.CommonServiceCatalogManager;
import dnt.itsnow.support.CommonServiceItemManager;
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

    @Bean
    public CommonServiceItemService commonServiceItemService(){
        return new CommonServiceItemManager();
    }
}
