/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultServiceConfig;
import dnt.itsnow.service.CommonAccountService;

/**
 * <h1>Common Account Service Configuration</h1>
 */
public class CommonAccountServiceConfig extends DefaultServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        exportService(CommonAccountService.class);
    }
}
