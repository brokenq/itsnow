/**
 * Developer: Kadvin Date: 14-9-18 上午8:34
 */
package dnt.itsnow.support;

import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.model.SystemJob;
import dnt.itsnow.service.SystemJobService;
import dnt.spring.Bean;
import org.springframework.stereotype.Service;

/**
 * <h1>系统任务生成器</h1>
 */
@Service
public class SystemJobManager extends Bean implements SystemJobService{
    @Override
    public SystemJob config(ItsnowHost host) {
        return new SystemJob(host, "/opt/system/", "provision_host.sh");
    }

    @Override
    public SystemJob quit(ItsnowHost host) {
        return new SystemJob(host, "/opt/system/", "quit_host.sh");
    }

    @Override
    public SystemJob create(ItsnowSchema schema) {
        return new SystemJob(schema.getHost(), "/opt/system/", "create_schema.sh " + schema.getName());
    }

    @Override
    public SystemJob drop(ItsnowSchema schema) {
        return new SystemJob(schema.getHost(), "/opt/system/", "drop_schema.sh " + schema.getName());
    }

    @Override
    public SystemJob deploy(ItsnowProcess process) {
        return new SystemJob(process.getHost(), "/opt/system/", "bin/deploy.sh " + process.getWd() + " " + process.getName());
    }

    @Override
    public SystemJob undeploy(ItsnowProcess process) {
        return new SystemJob(process.getHost(), "/opt/system/", "bin/undeploy.sh " + process.getWd() + " " + process.getName());
    }

    @Override
    public SystemJob start(ItsnowProcess process) {
        return new SystemJob(process.getHost(), process.getWd(), "bin/start.sh");
    }

    @Override
    public SystemJob stop(ItsnowProcess process) {
        return new SystemJob(process.getHost(), process.getWd(), "bin/stop.sh");
    }
}
