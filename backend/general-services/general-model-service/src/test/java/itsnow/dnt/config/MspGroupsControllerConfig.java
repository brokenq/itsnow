package itsnow.dnt.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.MspGroupService;
import dnt.itsnow.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.MspGroupsController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MspGroupsControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public MspGroupService mspGroupService(){
        return EasyMock.createMock(MspGroupService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public MspGroupsController mspGroupsController(){
        return new MspGroupsController();
    }

}
