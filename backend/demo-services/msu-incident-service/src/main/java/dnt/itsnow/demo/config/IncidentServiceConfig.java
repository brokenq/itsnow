package dnt.itsnow.demo.config;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.platform.config.DefaultServiceConfig;

/**
 * MSU Incident模块的服务配置
 */
public class IncidentServiceConfig extends DefaultServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        //importService(ProcessEngine.class);
        importService(ActivitiEngineService.class);
    }
}
