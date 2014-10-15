package itsnow.dnt.config;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.WorkflowService;
import dnt.itsnow.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.MspWorkflowsController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MspWorkflowsControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public WorkflowService mspWorkflowSerivce(){
        return EasyMock.createMock(WorkflowService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public ActivitiEngineService activitiEngineService(){
        return EasyMock.createMock(ActivitiEngineService.class);
    }

    @Bean
    public MspWorkflowsController mspWorkflowsController(){
        return new MspWorkflowsController();
    }

}
