/**
 * Developer: Kadvin Date: 14-7-11 下午3:49
 */
package dnt.itsnow.backend.config;

import dnt.itsnow.backend.service.SessionService;
import net.happyonroad.spring.service.AbstractServiceConfig;

/**
 * 服务配置
 */
public class SpringServiceConfig extends AbstractServiceConfig {

    @Override
    public void defineServices() {
        exportService(SessionService.class);
    }
}
