/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.remote.service.RestFacade;

/**
 * <h1>Class Usage</h1>
 */
public class GeneralAccountServiceConfig extends DefaultCommonServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        exportService(RestFacade.class);
    }
}
