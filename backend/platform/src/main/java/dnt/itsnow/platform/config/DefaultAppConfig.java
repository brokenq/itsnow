/**
 * Developer: Kadvin Date: 14-7-11
 */
package dnt.itsnow.platform.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 缺省的平台扩展应用Spring配置
 */
@Configuration
@Import({dnt.spring.DefaultAppConfig.class})
@ComponentScan("dnt.itsnow.support")
public class DefaultAppConfig {
}