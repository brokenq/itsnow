package itsnow.dnt.config;

import dnt.itsnow.service.MspWorkflowService;
import dnt.itsnow.service.WorkflowService;
import dnt.itsnow.support.MspWorkflowManager;
import dnt.itsnow.support.WorkflowManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class MspWorkflowManagerConfig extends MspWorkflowRepositoryConfig {

    @Bean
    public MspWorkflowService mspWorkflowSerivce(){
        return new MspWorkflowManager();
    }
}
