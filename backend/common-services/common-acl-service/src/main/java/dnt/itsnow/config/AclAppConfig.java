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
 * ACL Application Configuration
 */
@Configuration
@Import( DefaultAppConfig.class)
public class AclAppConfig implements InitializingBean {
    @Autowired
    DelegateSecurityConfigurer configurer;
    @Autowired
    PersistentTokenRepository  tokenRepository;
    @Autowired
    @Qualifier("grouped")
    UserDetailsService         userDetailsService;
    @Autowired
    AuthenticationProvider     authenticationProvider;

    @Override
    public void afterPropertiesSet() throws Exception {
        configurer.delegate(tokenRepository)
                  .delegate(userDetailsService)
                  .delegate(authenticationProvider);
    }
}
