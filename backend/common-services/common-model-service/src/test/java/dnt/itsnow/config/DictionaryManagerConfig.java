package dnt.itsnow.config;

import dnt.itsnow.service.DictionaryService;
import dnt.itsnow.support.DictionaryManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class DictionaryManagerConfig extends DictionaryRepositoryConfig {

    @Bean
    public DictionaryService processDictionarySerivce(){
        return new DictionaryManager();
    }
}
