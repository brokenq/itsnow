package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultAppConfig;
import dnt.itsnow.platform.web.security.DelegateSecurityConfigurer;
import dnt.itsnow.support.MsuRepositoryAuthenticationProvider;
import dnt.itsnow.support.MsuUserManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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
