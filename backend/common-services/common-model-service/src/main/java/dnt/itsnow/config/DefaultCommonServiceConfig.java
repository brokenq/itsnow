/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import net.happyonroad.platform.config.DefaultServiceConfig;
import dnt.itsnow.service.CommonUserService;

/**
 * <h1>Class Usage</h1>
 */
public class DefaultCommonServiceConfig extends DefaultServiceConfig{
    @Override
    public void defineServices() {
        super.defineServices();
        // User Service is used by SessionSupportController
        importService(CommonUserService.class, "plain", "plainUserService");
    }
}
