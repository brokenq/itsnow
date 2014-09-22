package itsnow.config;

import dnt.itsnow.serivce.MspGroupService;
import dnt.itsnow.service.GroupService;
import dnt.itsnow.support.GroupManager;
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
