/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import dnt.itsnow.mock.MutableAccountServiceMock;
import dnt.itsnow.service.MutableAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class MutableAccountsControllerConfig {
    @Bean
    public MutableAccountService mutableAccountService(){
        return new MutableAccountServiceMock();
    }
}
