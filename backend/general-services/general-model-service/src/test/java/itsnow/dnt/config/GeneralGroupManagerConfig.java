package itsnow.dnt.config;

import dnt.itsnow.service.GeneralGroupService;
import dnt.itsnow.support.GeneralGroupManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class GeneralGroupManagerConfig extends GeneralGroupRepositoryConfig {

    @Bean
    public GeneralGroupService generalGroupService(){
        return new GeneralGroupManager();
    }
}
