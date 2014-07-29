/**
 * @author XiongJie, Date: 14-7-29
 */
package dnt.itsnow.demo.config;

import dnt.itsnow.platform.config.DefaultAppConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The demo app config
 **/
@Configuration
@ComponentScan("dnt.itsnow.demo.support")
public class UserAppConfig extends DefaultAppConfig {
}
