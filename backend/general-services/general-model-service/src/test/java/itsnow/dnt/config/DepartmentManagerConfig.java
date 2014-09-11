package itsnow.dnt.config;

import dnt.itsnow.service.DepartmentService;
import dnt.itsnow.support.DepartmentManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class DepartmentManagerConfig extends DepartmentRepositoryConfig {

    @Bean
    public DepartmentService departmentSerivce(){
        return new DepartmentManager();
    }
}
