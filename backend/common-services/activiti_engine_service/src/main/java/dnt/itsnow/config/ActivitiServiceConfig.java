/**
 * @author XiongJie, Date: 14-7-29
 */
package dnt.itsnow.config;

import dnt.itsnow.api.ActivitiEngineService;
import net.happyonroad.platform.config.DefaultServiceConfig;
import org.activiti.engine.ProcessEngine;

/**
 * The activiti service config extracted from platform
 */
public class ActivitiServiceConfig extends DefaultServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        //工作流相关服务
        exportService(ProcessEngine.class);
        exportService(ActivitiEngineService.class);
    }
}
