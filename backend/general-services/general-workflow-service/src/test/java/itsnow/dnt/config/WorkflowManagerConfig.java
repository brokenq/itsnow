package itsnow.dnt.config;

import dnt.itsnow.service.CommonServiceItemService;
import dnt.itsnow.service.PrivateServiceCatalogService;
import dnt.itsnow.service.PrivateServiceItemService;
import dnt.itsnow.service.WorkflowService;
import dnt.itsnow.support.CommonServiceItemManager;
import dnt.itsnow.support.PrivateServiceCatalogManager;
import dnt.itsnow.support.PrivateServiceItemManager;
import dnt.itsnow.support.WorkflowManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class WorkflowManagerConfig extends WorkflowRepositoryConfig {

    @Bean
    public WorkflowService workflowSerivce(){
        return new WorkflowManager();
    }

    @Bean
    public CommonServiceItemService commonServiceItemService(){
        return new CommonServiceItemManager();
    }

    @Bean
    public PrivateServiceItemService privateServiceItemService(){
        return new PrivateServiceItemManager();
    }

    @Bean
    public PrivateServiceCatalogService privateServiceCatalogService(){
        return new PrivateServiceCatalogManager();
    }

}
