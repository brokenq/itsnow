/**
 * @author XiongJie, Date: 14-7-29
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultAppConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * the activiti app configuration
 */
@Configuration
@ImportResource("classpath:META-INF/activiti.xml")
@Import(DefaultAppConfig.class)
public class ActivitiAppConfig {
}
