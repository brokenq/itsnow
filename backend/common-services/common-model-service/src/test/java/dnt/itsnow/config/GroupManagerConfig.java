package dnt.itsnow.config;

import dnt.itsnow.service.GroupService;
import dnt.itsnow.support.GroupManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class GroupManagerConfig extends GroupRepositoryConfig {

    @Bean
    public GroupService groupSerivce(){
        return new GroupManager();
    }
}
