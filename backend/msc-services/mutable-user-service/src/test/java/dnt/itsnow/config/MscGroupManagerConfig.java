package dnt.itsnow.config;

import dnt.itsnow.service.MscGroupService;
import dnt.itsnow.support.MscGroupManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class MscGroupManagerConfig extends MscGroupRepositoryConfig {

    @Bean
    public MscGroupService mscGroupService(){
        return new MscGroupManager();
    }

}
