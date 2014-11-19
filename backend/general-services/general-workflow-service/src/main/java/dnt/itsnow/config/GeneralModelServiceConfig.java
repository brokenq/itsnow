/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.platform.service.AutoNumberService;
import dnt.itsnow.service.*;
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

        importService(AutoNumberService.class);
        importService(ActivitiEngineService.class);
        importService(CommonServiceItemService.class);
        importService(PrivateServiceItemService.class);
        importService(PrivateServiceCatalogService.class);

        exportService(WorkflowService.class);
    }

}
