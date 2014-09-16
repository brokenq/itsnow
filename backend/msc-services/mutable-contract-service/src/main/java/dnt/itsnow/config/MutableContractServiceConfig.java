/**
 * @author XiongJie, Date: 14-9-3
 */
package dnt.itsnow.config;

import dnt.itsnow.service.CommonAccountService;

/** The mutable contract service config */
public class MutableContractServiceConfig extends DefaultMutableServiceConfig{
    @Override
    public void defineServices() {
        super.defineServices();
        //importService(MutableAccountService.class);
        importService(CommonAccountService.class);
    }
}
