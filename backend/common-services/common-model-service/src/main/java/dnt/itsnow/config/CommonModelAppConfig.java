/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultAppConfig;
import dnt.itsnow.platform.web.security.DelegateSecurityConfigurer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * Common Model 模块的一般应用配置
 */
@Configuration
@Import({DefaultAppConfig.class})
public class CommonModelAppConfig implements InitializingBean {
    @Autowired
    DelegateSecurityConfigurer configurer;
    @Autowired
    PersistentTokenRepository  tokenRepository;
    @Autowired
    AuthenticationProvider     authenticationProvider;
    @Autowired
    @Qualifier("groupedUserService")
    UserDetailsService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        configurer.delegate(tokenRepository)
                  .delegate(authenticationProvider)
                  .delegate(userService);
    }
}
