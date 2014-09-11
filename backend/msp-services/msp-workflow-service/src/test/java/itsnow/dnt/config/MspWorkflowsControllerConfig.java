package itsnow.dnt.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.MspWorkflowService;
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
    public MspWorkflowService mspWorkflowSerivce(){
        return EasyMock.createMock(MspWorkflowService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public MspWorkflowsController mspWorkflowsController(){
        return new MspWorkflowsController();
    }

}
