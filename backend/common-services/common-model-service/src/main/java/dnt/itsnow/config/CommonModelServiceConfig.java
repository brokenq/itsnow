/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.config;

import net.happyonroad.platform.config.DefaultServiceConfig;
import net.happyonroad.platform.service.AutoNumberService;
import net.happyonroad.platform.web.security.DelegateSecurityConfigurer;
import dnt.itsnow.service.*;

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
        exportService(CommonServiceItemService.class);
        exportService(CommonContractService.class);
        exportService(DictionaryService.class);
    }
}
