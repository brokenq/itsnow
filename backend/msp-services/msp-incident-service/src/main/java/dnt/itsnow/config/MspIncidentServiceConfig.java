package dnt.itsnow.config;

import dnt.itsnow.api.ActivitiEngineService;

/**
 * MSP Incident模块的服务配置
 */
public class MspIncidentServiceConfig extends DefaultCommonServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(ActivitiEngineService.class);
    }
}
