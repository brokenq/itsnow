package dnt.itsnow.config;

import dnt.itsnow.service.PrivateServiceCatalogService;
import dnt.itsnow.service.PrivateServiceItemService;
import dnt.itsnow.support.PrivateServiceCatalogManager;
import dnt.itsnow.support.PrivateServiceItemManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>PrivateServiceCatalogManagerConfig</h1>
 */
@Configuration
public class PrivateServiceCatalogManagerConfig extends PrivateServiceCatalogRepositoryConfig {

    @Bean
    public PrivateServiceCatalogService privateServiceCatalogService(){
        return new PrivateServiceCatalogManager();
    }

    @Bean
    public PrivateServiceItemService publicServiceItemService(){
        return new PrivateServiceItemManager();
    }
}
