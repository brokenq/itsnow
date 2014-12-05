/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.config;

import net.happyonroad.platform.config.DefaultAppConfig;
import net.happyonroad.platform.web.security.DelegateSecurityConfigurer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
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

    @Override
    public void afterPropertiesSet() throws Exception {
        configurer.delegate(tokenRepository)
                  .delegate(authenticationProvider);
    }
}
