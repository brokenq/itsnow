/**
 * Developer: Kadvin Date: 14-7-15 下午1:14
 */
package dnt.itsnow.demo.config;

import dnt.itsnow.platform.config.DefaultServiceConfig;
import dnt.itsnow.demo.api.UserService;

/**
 * 会话模块的服务配置
 */
public class SessionServiceConfig extends DefaultServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(UserService.class);
    }
}
