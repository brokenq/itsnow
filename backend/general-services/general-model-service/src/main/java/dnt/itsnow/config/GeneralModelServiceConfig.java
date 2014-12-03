/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import net.happyonroad.cache.MutableCacheService;
import net.happyonroad.platform.service.AutoNumberService;
import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.service.CommonUserService;
import net.happyonroad.messaging.MessageBus;
import org.springframework.web.client.RestOperations;

/**
 * <h1>The general model service config</h1>
 */
public class GeneralModelServiceConfig extends DefaultGeneralServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(CommonAccountService.class);
        importService(CommonUserService.class);
        exportService(RestOperations.class);

        importService(AutoNumberService.class);

        // export local redis service
        exportService(MutableCacheService.class, "local", "localCacheService") ;
        exportService(MessageBus.class, "local", "localMessageBus") ;
    }

}
