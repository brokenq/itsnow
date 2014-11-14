package itsnow.dnt.config;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.WorkflowService;
import dnt.itsnow.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.WorkflowsController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkflowsControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public WorkflowService workflowSerivce(){
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
    public WorkflowsController workflowsController(){
        return new WorkflowsController();
    }

}
