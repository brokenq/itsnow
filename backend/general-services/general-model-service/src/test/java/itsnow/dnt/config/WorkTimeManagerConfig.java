package itsnow.dnt.config;

import dnt.itsnow.service.WorkTimeService;
import dnt.itsnow.support.WorkTimeManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class WorkTimeManagerConfig extends WorkTimeRepositoryConfig {

    @Bean
    public WorkTimeService workTimeSerivce(){
        return new WorkTimeManager();
    }
}
