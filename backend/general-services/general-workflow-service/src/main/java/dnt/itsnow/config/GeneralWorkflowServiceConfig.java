/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.api.ActivitiEngineService;
import net.happyonroad.platform.service.AutoNumberService;
import dnt.itsnow.service.CommonServiceItemService;
import dnt.itsnow.service.PrivateServiceCatalogService;
import dnt.itsnow.service.PrivateServiceItemService;
import dnt.itsnow.service.WorkflowService;

/**
 * <h1>The general model service config</h1>
 */
public class GeneralWorkflowServiceConfig extends DefaultGeneralServiceConfig {

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
