package dnt.itsnow.config;

import net.happyonroad.platform.config.DefaultAppConfig;
import net.happyonroad.platform.web.security.DelegateSecurityConfigurer;
import dnt.itsnow.support.MsuRepositoryAuthenticationProvider;
import dnt.itsnow.support.MsuUserManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * MSU Model 模块的一般应用配置
 */
@Configuration
@Import({DefaultAppConfig.class})
public class MsuGuiAppConfig implements InitializingBean {

    @Autowired
    DelegateSecurityConfigurer configurer;

    @Autowired
    MsuRepositoryAuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("msuUserService")
    MsuUserManager userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        configurer.delegate(authenticationProvider)
                  .delegate(userService);
    }
}
