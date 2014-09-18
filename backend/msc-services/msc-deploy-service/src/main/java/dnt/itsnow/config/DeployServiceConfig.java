/**
 * Developer: Kadvin Date: 14-9-18 上午10:51
 */
package dnt.itsnow.config;

import dnt.itsnow.service.SystemInvokeService;

/**
 * <h1>Deploy Service Config</h1>
 */
public class DeployServiceConfig extends DefaultMutableServiceConfig{
    @Override
    public void defineServices() {
        super.defineServices();
        importService(SystemInvokeService.class);
    }
}
