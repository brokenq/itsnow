package dnt.itsnow.config;

import dnt.itsnow.api.ActivitiEngineService;

/**
 * MSU Incident模块的服务配置
 */
public class MsuIncidentServiceConfig extends DefaultCommonServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(ActivitiEngineService.class);

    }

}
