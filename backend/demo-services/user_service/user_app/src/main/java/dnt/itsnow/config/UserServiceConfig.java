/**
 * Developer: Kadvin Date: 14-7-15 下午1:11
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultServiceConfig;
import dnt.itsnow.services.api.UserService;

/**
 * User App的服务配置
 */
public class UserServiceConfig extends DefaultServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        exportService(UserService.class);
    }
}
