/**
 * Developer: Kadvin Date: 14-6-26 上午11:18
 */
package dnt.itsnow.backend.web.config;

import dnt.spring.DefaultAppConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h2>被Spring Component Framework加载的应用配置</h2>
 *
 * 加载的逻辑顺序为:
 * <pre>
 * Spring Component AppLauncher
 *   |- SpringAppConfig
 *   |   |- JettyServer
 *   |   |    |- AnnotationConfiguration
 *   |   |    |    |- WebAppLoader
 *   |   |    |    |    |- RestWebConfig
 *   |   |    |    |    |    |- All kinds of controllers
 * </pre>
 */
@Configuration
@Import({DefaultAppConfig.class})
public class SpringAppConfig {

    @Bean
    public JettyServer jettyServer(){
        return new JettyServer();
    }
}
