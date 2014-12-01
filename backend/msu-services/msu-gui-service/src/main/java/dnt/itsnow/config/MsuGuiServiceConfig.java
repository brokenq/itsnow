package dnt.itsnow.config;

import dnt.itsnow.platform.web.security.DelegateSecurityConfigurer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * <h1>Msu gui model service config</h1>
 */
public class MsuGuiServiceConfig extends DefaultGeneralServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();

        importService(DelegateSecurityConfigurer.class);
    }

}
