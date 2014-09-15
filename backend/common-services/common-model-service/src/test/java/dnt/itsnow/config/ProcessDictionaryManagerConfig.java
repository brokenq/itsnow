package dnt.itsnow.config;

import dnt.itsnow.service.ProcessDictionaryService;
import dnt.itsnow.support.ProcessDictionaryManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class ProcessDictionaryManagerConfig extends ProcessDictionaryRepositoryConfig {

    @Bean
    public ProcessDictionaryService processDictionarySerivce(){
        return new ProcessDictionaryManager();
    }
}
