/**
 * Developer: Kadvin Date: 14-7-14 下午4:22
 */
package dnt.itsnow.platform;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.platform.services.ServicePackageManager;
import dnt.itsnow.platform.support.ServicePackageEventForwarder;
import dnt.itsnow.platform.support.JettyServer;
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
 *   |   |    |    |- SpringMvcLoader
 *   |   |    |    |    |- SpringSecurityConfig
 *   |   |    |    |    |   |- SpringMvcConfig
 *   |   |- ServicePackageManager
 *   |   |    | - All kinds of service app in repository folder
 * </pre>
 */
@org.springframework.context.annotation.Configuration
@Import({DefaultAppConfig.class, DatabaseConfig.class})
public class PlatformConfiguration {

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

    //用于把平台context中的事件转发给 躲在dispatcher servlet 中的 Spring Mvc Context
    @Bean
    public ServicePackageEventForwarder eventForwarder(){
        return new ServicePackageEventForwarder();
    }

}
