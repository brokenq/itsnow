package itsnow.dnt.config;

import dnt.itsnow.service.RoleService;
import dnt.itsnow.support.RoleManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class RoleManagerConfig extends RoleRepositoryConfig {

    @Bean
    public RoleService roleSerivce(){
        return new RoleManager();
    }
}
