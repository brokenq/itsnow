/**
 * Developer: Kadvin Date: 14-9-18 上午10:40
 */
package dnt.itsnow.config;

import dnt.itsnow.service.SystemInvokeService;
import org.springframework.scheduling.TaskScheduler;

/**
 * <h1>系统调用模块的服务配置</h1>
 */
public class SystemInvokeServiceConfig extends DefaultMutableServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        importService(TaskScheduler.class, "system");
        exportService(SystemInvokeService.class);
    }
}
