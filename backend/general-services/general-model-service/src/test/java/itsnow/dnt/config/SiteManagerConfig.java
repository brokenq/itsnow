package itsnow.dnt.config;

import dnt.itsnow.service.SiteService;
import dnt.itsnow.support.SiteManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class SiteManagerConfig extends SiteRepositoryConfig {

    @Bean
    public SiteService siteSerivce(){
        return new SiteManager();
    }
}
