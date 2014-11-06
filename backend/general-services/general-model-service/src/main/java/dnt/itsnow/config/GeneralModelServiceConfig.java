/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.service.AutoNumberService;
import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.service.WorkflowService;
import org.springframework.web.client.RestOperations;
import dnt.cache.MutableCacheService;
import dnt.messaging.MessageBus;

/**
 * <h1>The general model service config</h1>
 */
public class GeneralModelServiceConfig extends DefaultGeneralServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(CommonAccountService.class);
        exportService(RestOperations.class);
        exportService(WorkflowService.class);

        importService(AutoNumberService.class);

        // export local redis service
        exportService(MutableCacheService.class, "local", "localCacheService") ;
        exportService(MessageBus.class, "local", "localMessageBus") ;
    }

}
