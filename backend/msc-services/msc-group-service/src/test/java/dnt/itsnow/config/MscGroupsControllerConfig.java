package dnt.itsnow.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.MscGroupService;
import dnt.itsnow.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.MscGroupsController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MscGroupsControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public MscGroupService mscGroupService(){
        return EasyMock.createMock(MscGroupService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public MscGroupsController mscGroupsController(){
        return new MscGroupsController();
    }

}
