/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.config;

import dnt.itsnow.service.AccountService;

/** The mutable user service manager */
public class MutableUserServiceConfig extends DefaultMutableServiceConfig{
    @Override
    public void defineServices() {
        super.defineServices();
        importService(AccountService.class);
    }
}
