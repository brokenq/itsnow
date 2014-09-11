package dnt.itsnow.config;

import dnt.itsnow.service.ProcessDictionarySerivce;
import dnt.itsnow.support.ProcessDictionaryManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class ProcessDictionaryManagerConfig extends ProcessDictionaryRepositoryConfig {

    @Bean
    public ProcessDictionarySerivce processDictionarySerivce(){
        return new ProcessDictionaryManager();
    }
}
