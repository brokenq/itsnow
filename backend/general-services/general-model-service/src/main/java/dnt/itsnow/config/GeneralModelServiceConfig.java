/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.service.CommonAccountService;

/**
 * <h1>Class Usage</h1>
 */
public class GeneralModelServiceConfig extends DefaultGeneralServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        importService(CommonAccountService.class);
    }
}
