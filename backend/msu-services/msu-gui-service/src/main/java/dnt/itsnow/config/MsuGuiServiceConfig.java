package dnt.itsnow.config;

import net.happyonroad.platform.web.security.DelegateSecurityConfigurer;

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
