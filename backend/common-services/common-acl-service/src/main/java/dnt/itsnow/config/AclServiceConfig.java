/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultServiceConfig;
import dnt.itsnow.platform.web.security.DelegateSecurityConfigurer;
import dnt.itsnow.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 缺省的服务配置
 */
public class AclServiceConfig extends DefaultServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(DelegateSecurityConfigurer.class);
        importService(UserService.class, "*", "origin");
    }
}
