package dnt.itsnow.config;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.service.*;

/**
 * MSU Incident模块的服务配置
 */
public class MsuIncidentServiceConfig extends DefaultCommonServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(ActivitiEngineService.class);
        importService(WorkflowService.class);
        importService(CommonServiceItemService.class);
        importService(DictionaryService.class);
        importService(CommonContractService.class);
        importService(CommonAccountService.class);

    }

}
