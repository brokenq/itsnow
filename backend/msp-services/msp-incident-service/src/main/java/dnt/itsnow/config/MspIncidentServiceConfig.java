package dnt.itsnow.config;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.service.CommonServiceItemService;
import dnt.itsnow.service.DictionaryService;
import dnt.itsnow.service.WorkflowService;

/**
 * MSP Incident模块的服务配置
 */
public class MspIncidentServiceConfig extends DefaultCommonServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(ActivitiEngineService.class);
        importService(WorkflowService.class);
        importService(CommonServiceItemService.class);
        importService(DictionaryService.class);
        importService(CommonAccountService.class);
    }
}
