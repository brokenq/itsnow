package itsnow.dnt.config;

import dnt.itsnow.service.StaffService;
import dnt.itsnow.support.StaffManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class StaffManagerConfig extends StaffRepositoryConfig {

    @Bean
    public StaffService staffSerivce(){
        return new StaffManager();
    }
}
