package itsnow.dnt.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.GeneralGroupService;
import dnt.itsnow.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.GeneralGroupsController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralGroupsControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public GeneralGroupService generalGroupService(){
        return EasyMock.createMock(GeneralGroupService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public GeneralGroupsController generalGroupsController(){
        return new GeneralGroupsController();
    }

}
