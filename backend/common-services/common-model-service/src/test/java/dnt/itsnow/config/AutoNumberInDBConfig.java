/**
 * Developer: Kadvin Date: 14/11/5 上午11:20
 */
package dnt.itsnow.config;

import dnt.itsnow.support.AutoNumberInDB;
import org.springframework.context.annotation.Bean;

/**
 * The auto number in db config
 */
public class AutoNumberInDBConfig extends SequenceRepositoryConfig {
    @Bean
    public AutoNumberInDB autoNumberInDB(){
        return new AutoNumberInDB();
    }
}
