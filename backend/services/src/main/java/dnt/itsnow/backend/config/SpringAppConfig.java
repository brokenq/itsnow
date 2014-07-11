/**
 * Developer: Kadvin Date: 14-7-11
 */
package dnt.itsnow.backend.config;

import dnt.spring.DefaultAppConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DefaultAppConfig.class, DatabaseConfig.class})
@ComponentScan("dnt.itsnow.backend.support")
public class SpringAppConfig  {
}