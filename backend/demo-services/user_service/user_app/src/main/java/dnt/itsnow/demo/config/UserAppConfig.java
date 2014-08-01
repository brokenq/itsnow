/**
 * @author XiongJie, Date: 14-7-29
 */
package dnt.itsnow.demo.config;

import dnt.itsnow.platform.config.DefaultAppConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The demo app config
 **/
@Configuration
@ComponentScan("dnt.itsnow.demo.support")
@Import(DefaultAppConfig.class)
public class UserAppConfig {
}
