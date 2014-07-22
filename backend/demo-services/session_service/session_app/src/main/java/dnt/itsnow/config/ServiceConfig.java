/**
 * Developer: Kadvin Date: 14-7-15 下午1:14
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.services.DefaultServiceConfig;
import dnt.itsnow.services.api.UserService;

/**
 * 会话模块的服务配置
 */
public class ServiceConfig extends DefaultServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(UserService.class);
    }
}
