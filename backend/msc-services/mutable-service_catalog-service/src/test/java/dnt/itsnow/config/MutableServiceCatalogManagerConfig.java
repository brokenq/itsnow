package dnt.itsnow.config;

import dnt.itsnow.service.PublicServiceCatalogService;
import dnt.itsnow.service.PublicServiceItemService;
import dnt.itsnow.support.PublicServiceCatalogManager;
import dnt.itsnow.support.PublicServiceItemManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>CommonServiceCatalogManagerConfig</h1>
 */
@Configuration
public class MutableServiceCatalogManagerConfig extends MutableServiceCatalogRepositoryConfig {

    @Bean
    public PublicServiceCatalogService publicServiceCatalogService(){
        return new PublicServiceCatalogManager();
    }

    @Bean
    public PublicServiceItemService publicServiceItemService(){
        return new PublicServiceItemManager();
    }
}
