package dnt.itsnow.config;

import dnt.itsnow.service.CommonMenuItemService;
import dnt.itsnow.support.CommonMenuItemManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonMenuItemManagerConfig extends CommonMenuItemRepositoryConfig {

    @Bean
    public CommonMenuItemService commonMenuItemService(){
        return new CommonMenuItemManager();
    }

}
