/**
 * Developer: Kadvin Date: 14-7-14 下午4:22
 */
package dnt.itsnow.platform;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.platform.services.ServicePackageManager;
import dnt.itsnow.platform.web.support.JettyServer;
import dnt.spring.DefaultAppConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 平台启动配置
 * <h2>被Spring Component Framework加载的应用配置</h2>
 *
 * 加载的逻辑顺序为:
 * <pre>
 * Spring Component AppLauncher
 *   |- Platform Configuration
 *   |   |- Config module
 *   |   |- Database module
 *   |   |- JettyServer
 *   |   |    |- AnnotationConfiguration
 *   |   |    |    |- WebAppLoader
 *   |   |    |    |    |- SpringMvcConfig
 *   |   |- ServicePackageManager
 *   |   |    | - All kinds of service app in repository folder
 * </pre>
 */
@org.springframework.context.annotation.Configuration
@Import({DefaultAppConfig.class, DatabaseConfig.class})
public class AppConfiguration {

    // 用于启动WEB应用
    @Bean
    public JettyServer jettyServer(){
        return new JettyServer();
    }

    // 用于加载扩展服务模块
    @Bean
    public ServicePackageManager pkgManager(){
        return new ServicePackageManager();
    }

}
