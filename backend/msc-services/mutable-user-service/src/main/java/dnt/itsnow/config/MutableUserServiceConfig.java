/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultServiceConfig;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Description */
public class MutableUserServiceConfig extends DefaultServiceConfig{
    @Override
    public void defineServices() {
        super.defineServices();
        importService(PasswordEncoder.class);
    }
}
