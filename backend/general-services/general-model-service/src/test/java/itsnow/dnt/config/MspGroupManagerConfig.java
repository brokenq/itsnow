package itsnow.dnt.config;

import dnt.itsnow.service.MspGroupService;
import dnt.itsnow.support.MspGroupManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class MspGroupManagerConfig extends MspGroupRepositoryConfig {

    @Bean
    public MspGroupService mspGroupSerivce(){
        return new MspGroupManager();
    }
}
