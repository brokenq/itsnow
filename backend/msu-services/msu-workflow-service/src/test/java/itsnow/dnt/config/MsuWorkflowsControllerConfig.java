package itsnow.dnt.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.MsuWorkflowService;
import dnt.itsnow.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.MsuWorkflowsController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MsuWorkflowsControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public MsuWorkflowService mspWorkflowSerivce(){
        return EasyMock.createMock(MsuWorkflowService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public MsuWorkflowsController mspWorkflowsController(){
        return new MsuWorkflowsController();
    }

}
