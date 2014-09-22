/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.service.CommonAccountService;
import org.springframework.web.client.RestOperations;

/**
 * <h1>The general model service config</h1>
 */
public class GeneralModelServiceConfig extends DefaultGeneralServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        importService(CommonAccountService.class);
        exportService(RestOperations.class);
    }
}
