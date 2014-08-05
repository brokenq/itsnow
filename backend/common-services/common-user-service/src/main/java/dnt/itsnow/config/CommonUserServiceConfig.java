/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultServiceConfig;
import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.service.UserService;

/**
 * User 模块的服务
 */
public class CommonUserServiceConfig extends DefaultServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        importService(CommonAccountService.class);
        exportService(UserService.class, "plain");
    }
}
