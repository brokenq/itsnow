package dnt.itsnow.config;

import dnt.itsnow.service.CommonMenuItemService;
import dnt.itsnow.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.CommonMenuItemController;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonMenuItemControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public CommonMenuItemService commonMenuItemService(){
        return EasyMock.createMock(CommonMenuItemService.class);
    }

}
