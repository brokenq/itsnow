package dnt.itsnow.config;

import dnt.itsnow.service.MenuItemService;
import dnt.itsnow.support.MenuItemManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemManagerConfig extends MenuItemRepositoryConfig {

    @Bean
    public MenuItemService commonMenuItemService(){
        return new MenuItemManager();
    }

}
