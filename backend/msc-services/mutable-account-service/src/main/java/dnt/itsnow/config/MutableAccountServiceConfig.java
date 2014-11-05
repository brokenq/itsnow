/**
 * @author XiongJie, Date: 14-9-3
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.service.AutoNumberService;
import dnt.itsnow.service.MutableUserService;

/** The mutable account service config */
public class MutableAccountServiceConfig extends DefaultMutableServiceConfig{
    @Override
    public void defineServices() {
        super.defineServices();
        importService(MutableUserService.class);
        importService(AutoNumberService.class);
    }
}
