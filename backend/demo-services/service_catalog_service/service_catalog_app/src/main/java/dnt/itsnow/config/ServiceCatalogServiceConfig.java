/**
 * Developer: Kadvin Date: 14-7-15 下午1:14
 */
package dnt.itsnow.config;

import net.happyonroad.platform.config.DefaultServiceConfig;
import dnt.itsnow.api.ServiceCatalogService;

/**
 * 服务目录模块的服务配置
 */
public class ServiceCatalogServiceConfig extends DefaultServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        exportService(ServiceCatalogService.class);
    }
}
