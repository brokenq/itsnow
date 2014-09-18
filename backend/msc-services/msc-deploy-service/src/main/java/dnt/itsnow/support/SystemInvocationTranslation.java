/**
 * Developer: Kadvin Date: 14-9-18 上午8:34
 */
package dnt.itsnow.support;

import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.model.SystemInvocation;
import dnt.itsnow.service.SystemInvocationTranslator;
import dnt.spring.Bean;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Set;

/**
 * <h1>系统任务生成器</h1>
 */
@Service
public class SystemInvocationTranslation extends Bean implements SystemInvocationTranslator {
    @Override
    public SystemInvocation config(ItsnowHost host) {
        return new SystemInvocation(host.getAddress(),
                                    "/opt/system/", "provision_host.sh",
                                    encapsulate(host.getConfiguration(), "host"));
    }

    @Override
    public SystemInvocation quit(ItsnowHost host) {
        return new SystemInvocation(host.getAddress(),
                                    "/opt/system/",  "quit_host.sh",
                                    encapsulate(host.getConfiguration(), "host"));
    }

    @Override
    public SystemInvocation create(ItsnowSchema schema) {
        ItsnowHost host = schema.getHost();
        return new SystemInvocation(host.getAddress(),
                                    "/opt/system/", "create_schema.sh " + schema.getName(),
                                    encapsulate(host.getConfiguration(), "host"),
                                    encapsulate( schema.getConfiguration(), "schema"));
    }

    @Override
    public SystemInvocation drop(ItsnowSchema schema) {
        ItsnowHost host = schema.getHost();
        return new SystemInvocation(host.getAddress(),
                                    "/opt/system/",
                                    "drop_schema.sh " + schema.getName(),
                                    encapsulate(host.getConfiguration(), "host"),
                                    encapsulate(schema.getConfiguration(), "schema"));
    }

    @Override
    public SystemInvocation deploy(ItsnowProcess process) {
        ItsnowHost host = process.getHost();
        return new SystemInvocation(host.getAddress(),
                                    "/opt/system/",
                                    "bin/deploy.sh " + process.getWd() + " " + process.getName(),
                                    encapsulate(host.getConfiguration(), "host"),
                                    encapsulate(process.getSchema().getConfiguration(), "schema"),
                                    encapsulate(process.getConfiguration(), "process"));
    }

    @Override
    public SystemInvocation undeploy(ItsnowProcess process) {
        ItsnowHost host = process.getHost();
        return new SystemInvocation(host.getAddress(),
                                    "/opt/system/",
                                    "bin/undeploy.sh " + process.getWd() + " " + process.getName(),
                                    encapsulate(host.getConfiguration(), "host"),
                                    encapsulate(process.getSchema().getConfiguration(), "schema"),
                                    encapsulate(process.getConfiguration(), "process"));
    }

    @Override
    public SystemInvocation start(ItsnowProcess process) {
        ItsnowHost host = process.getHost();
        return new SystemInvocation(host.getAddress(),
                                    process.getWd(), "bin/start.sh",
                                    encapsulate(host.getConfiguration(), "host"),
                                    encapsulate(process.getSchema().getConfiguration(), "schema"),
                                    encapsulate(process.getConfiguration(), "process"));
    }

    @Override
    public SystemInvocation stop(ItsnowProcess process) {
        ItsnowHost host = process.getHost();
        return new SystemInvocation(host.getAddress(),
                                    process.getWd(), "bin/stop.sh",
                                    encapsulate(host.getConfiguration(), "host"),
                                    encapsulate(process.getSchema().getConfiguration(), "schema"),
                                    encapsulate(process.getConfiguration(), "process"));
    }

    protected Properties encapsulate(Properties origin, String prefix){
        Properties encapsulated = new Properties();
        if( origin == null ||  origin.isEmpty() ) {
            return encapsulated;
        }
        Set<String> names = origin.stringPropertyNames();
        for (String propertyName : names) {
            String value = origin.getProperty(propertyName);
            encapsulated.put(prefix + "." + propertyName, value);
        }
        return encapsulated;
    }
}
