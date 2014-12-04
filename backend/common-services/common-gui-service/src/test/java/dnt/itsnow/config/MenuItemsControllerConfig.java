package dnt.itsnow.config;

import dnt.itsnow.service.MenuItemService;
import net.happyonroad.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.MenuItemsController;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemsControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public MenuItemService commonMenuItemService(){
        return EasyMock.createMock(MenuItemService.class);
    }

    @Bean
    public MenuItemsController commonMenuItemController(){
        return new MenuItemsController();
    }

}
