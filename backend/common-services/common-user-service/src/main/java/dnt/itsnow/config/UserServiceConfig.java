/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultServiceConfig;
import dnt.itsnow.service.UserService;

/**
 * User 模块的服务
 */
public class UserServiceConfig extends DefaultServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        exportService(UserService.class);
    }
}
