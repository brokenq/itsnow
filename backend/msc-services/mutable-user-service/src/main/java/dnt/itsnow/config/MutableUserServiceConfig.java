/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.config;

import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.service.MutableUserService;

/** The mutable user service config */
public class MutableUserServiceConfig extends DefaultMutableServiceConfig{
    @Override
    public void defineServices() {
        super.defineServices();
        importService(CommonAccountService.class);
        //for mutable account service implementations to use
        exportService(MutableUserService.class);
    }
}
