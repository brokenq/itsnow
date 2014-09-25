package dnt.itsnow.config;

import dnt.itsnow.service.MscRoleService;
import dnt.itsnow.support.MscRoleManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class MscRoleManagerConfig extends MscRoleRepositoryConfig {

    @Bean
    public MscRoleService mscRoleSerivce(){
        return new MscRoleManager();
    }
}
