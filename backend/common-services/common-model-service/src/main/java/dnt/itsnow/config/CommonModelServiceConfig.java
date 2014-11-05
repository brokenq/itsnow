/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultServiceConfig;
import dnt.itsnow.platform.service.AutoNumberService;
import dnt.itsnow.platform.web.security.DelegateSecurityConfigurer;
import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.service.CommonUserService;

/**
 * Common Model 模块的服务配置
 */
public class CommonModelServiceConfig extends DefaultServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        importService(DelegateSecurityConfigurer.class);

        exportService(CommonAccountService.class);
        exportService(CommonUserService.class, "plain", "plainUserService");
        exportService(AutoNumberService.class);
    }
}
