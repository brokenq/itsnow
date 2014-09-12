package itsnow.dnt.config;

import dnt.itsnow.service.MsuWorkflowService;
import dnt.itsnow.support.MsuWorkflowManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class MsuWorkflowManagerConfig extends MsuWorkflowRepositoryConfig {

    @Bean
    public MsuWorkflowService mspWorkflowSerivce(){
        return new MsuWorkflowManager();
    }
}
