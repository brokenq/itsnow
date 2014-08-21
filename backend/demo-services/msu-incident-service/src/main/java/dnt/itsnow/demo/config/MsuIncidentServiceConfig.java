package dnt.itsnow.demo.config;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.config.DefaultCommonServiceConfig;

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
