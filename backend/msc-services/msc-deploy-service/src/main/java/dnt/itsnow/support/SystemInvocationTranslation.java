/**
 * Developer: Kadvin Date: 14-9-18 上午8:34
 */
package dnt.itsnow.support;

import dnt.itsnow.model.*;
import dnt.itsnow.service.SystemInvocationTranslator;
import dnt.itsnow.system.Process;
import dnt.spring.Bean;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

/**
 * <h1>系统任务生成器</h1>
 * <p/>
 * 这里并未粗暴的将所有的任务变成一个简单的script调用，这是为了让script更易于开发
 * <ol>
 * 设计原则是：
 * <li> 由本类负责将系统中的对象(host, schema, process)作为参数，提供给被执行的脚本
 * <li> 每个script都完成其特定工作，该工作应该简单，明了；script之间尽量减少相互调用
 * <li> 由本类负责将多个script串联起来(system invocation chain)
 * </ol>
 */
@Service
public class SystemInvocationTranslation extends Bean implements SystemInvocationTranslator {

    @Override
    public SystemInvocation provision(final ItsnowHost host) {
        // First:  on msc host: run $msc_release/script/msc/trust_me.sh target_host user passwd
        //   make the target host trust msc host
        // Second: on msc host: run $msc_release/script/msc/prepare_host.sh target_host
        //   copy binaries and $msc_release/script/platform/*.sh to target_host /opt/system/
        // Third:  on target_host run /opt/system/script/provision.sh
        //   install redis/mysql etc
        return new LocalInvocation() {
            public int perform(Process process) throws Exception {
                return process.run("./trust_me.sh",
                                   host.getAddress(),
                                   host.getProperty("user"),
                                   host.getProperty("password"));
            }
        }.timeout("1m").next(new LocalInvocation() {
            public int perform(Process process) throws Exception {
                return process.run("./prepare_host.sh", host.getAddress());
            }
        }.timeout("3m")).next(new RemoteInvocation(host.getAddress()) {
            public int perform(Process ssh) throws Exception {
                return ssh.run("./provision.sh");
            }
        }.timeout("5m"/*最后开通最长用时5分钟*/)).next(new RemoteInvocation(host.getAddress()) {
            //将 ci 上 特定版本的 msu 下载到目标主机的特定路径，并做好link
            @Override
            public int perform(Process ssh) throws Exception {
                return ssh.run("./prepare_ms.sh", "msu", host.getProperty("msu.version"));
            }
        }).next(new RemoteInvocation(host.getAddress()) {
            //将 ci 上 特定版本的 msp 下载到目标主机的特定路径，并做好link
            @Override
            public int perform(Process ssh) throws Exception {
                return ssh.run("./prepare_ms.sh", "msp", host.getProperty("msp.version"));
            }
        });
    }

    @Override
    public SystemInvocation delist(final ItsnowHost host) {
        return new RemoteInvocation(host.getAddress()) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./delist.sh");
            }
        }.next(new LocalInvocation() {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./delist_host.sh", host.getAddress());
            }
        });
    }

    @Override
    public SystemInvocation create(final ItsnowSchema schema) {
        return new RemoteInvocation(schema.getHostAddress()) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./create_db.sh", schema.getName(),
                                   schema.getProperty("user"),
                                   schema.getProperty("password"));
            }
        };
    }

    @Override
    public SystemInvocation drop(final ItsnowSchema schema) {
        return new RemoteInvocation(schema.getHostAddress()) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./drop_db.sh", schema.getName(),
                                   schema.getProperty("user"));
            }
        };
    }

    @Override
    public SystemInvocation deploy(final ItsnowProcess itsnowProcess) {
        final String address = itsnowProcess.getHostAddress();
        return (new RemoteInvocation(address) {
            // 1. 生成需要部署的msc/msu的目录(做好各种link/配置文件copy工作)
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./prepare_deploy.sh",
                                   itsnowProcess.getAccount().getType().toLowerCase(),
                                   itsnowProcess.getName());
            }
        }).next(new ScpTask(itsnowProcess, nowVars(itsnowProcess), "config/now.vars"))
          .next(new ScpTask(itsnowProcess, dbVars(itsnowProcess), "db/migrate/environments/production.vars"))
          .next(new ScpTask(itsnowProcess, shVars(itsnowProcess), "bin/sh.vars"))
          .next(new ScpTask(itsnowProcess, wrapperVars(itsnowProcess), "config/wrapper.vars"))
          .next(new ScpTask(itsnowProcess, nginxVars(itsnowProcess), "config/nginx.vars"))
          .next(new RemoteInvocation(address) {
              // 7. 实际开始部署
              @Override
              public int perform(Process process) throws Exception {
                  return process.run("./deploy.sh", itsnowProcess.getName());
              }
          })
          .next(new RemoteInvocation(address) {
              // 8. 进行db migrate
              @Override
              public int perform(Process process) throws Exception {
                  return process.run("./migrate_ms.sh", itsnowProcess.getName());
              }
          })
          .next(new LocalInvocation() {
              // 9. 将生成的 nginx.conf copy到本机(msc所在机器) 并予以加载
              @Override
              public int perform(Process process) throws Exception {
                  return process.run("./proxy_ms.sh", itsnowProcess.getHostAddress(), itsnowProcess.getName());
              }
          });
    }

    @Override
    public SystemInvocation undeploy(final ItsnowProcess itsnowProcess) {
        return new LocalInvocation(){
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./unproxy_ms.sh", itsnowProcess.getName());
            }
        }.next(new RemoteInvocation(itsnowProcess.getHostAddress()) {
            // 暂时看到只需要远程执行一条语句
            //  卸载之前的备份等工作，暂时没有归在这里，而是通过外部工作
            // 而卸载之前的停止，清理等工作，也是需要外部执行(甚至应该是运营人员通过管理界面执行的）
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./undeploy.sh", itsnowProcess.getName());
            }
        });
    }

    @Override
    public SystemInvocation start(final ItsnowProcess itsnowProcess) {
        return new RemoteInvocation(itsnowProcess.getHostAddress()) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./start_ms.sh", itsnowProcess.getName());
            }
        };
    }

    @Override
    public SystemInvocation stop(final ItsnowProcess itsnowProcess) {
        return new RemoteInvocation(itsnowProcess.getHostAddress()) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./stop_ms.sh", itsnowProcess.getName());
            }
        };
    }

    static class ScpTask extends LocalInvocation {
        private ItsnowProcess itsnowProcess;
        private File          varsFile;
        private String        targetName;

        ScpTask(ItsnowProcess itsnowProcess, File varsFile, String targetName) {
            this.itsnowProcess = itsnowProcess;
            this.varsFile = varsFile;
            this.targetName = targetName;
        }

        @Override
        public int perform(Process process) throws Exception {
            String address = itsnowProcess.getHostAddress();
            //生成这个部署实体的属性文件
            String targetPath = String.format("/opt/itsnow/%s/%s", itsnowProcess.getName(), targetName);
            String target = String.format("root@%s:%s", address, targetPath);
            return process.run("scp", varsFile.getAbsolutePath(), target);
        }
    }

    File nowVars(ItsnowProcess process) {
        Properties props = new Properties(process.getConfiguration());
        ItsnowSchema schema = process.getSchema();
        String port = schema.getProperty("port");
        if (port == null) port = "3306";
        props.setProperty("app.name", process.getDisplayName());
        props.setProperty("app.id", process.getIdentifier());
        props.setProperty("db.host", schema.getHostAddress());
        props.setProperty("db.user", schema.getProperty("user"));
        props.setProperty("db.password", schema.getProperty("password"));
        props.setProperty("db.port", port);
        // http jmx redis configuration is stored in process configuration
        //props.setProperty("http.port", process.getProperty("http.port"));
        return createProperties(props, "now.vars");
    }

    File dbVars(ItsnowProcess process) {
        Properties props = new Properties();
        ItsnowSchema schema = process.getSchema();
        String port = schema.getProperty("port");
        if (port == null) port = "3306";
        props.setProperty("db.host", schema.getHostAddress());
        props.setProperty("db.port", port);
        props.setProperty("db.name", schema.getName());
        props.setProperty("username", schema.getProperty("user"));
        props.setProperty("password", schema.getProperty("password"));
        return createProperties(props, "production.vars");
    }

    File shVars(ItsnowProcess process) {
        Properties props = new Properties();
        props.setProperty("APP_NAME", process.getDisplayName());
        props.setProperty("APP_PORT", process.getProperty("rmi.port"));
        props.setProperty("jmxremote.port", process.getProperty("jmx.port"));
        props.setProperty("address", process.getProperty("debug.port"));
        return createProperties(props, "sh.vars");
    }

    File wrapperVars(ItsnowProcess process) {
        // for config/wrapper.conf, bin/itsnow-$id
        Properties props = new Properties();
        String home = "/opt/itsnow/" + process.getName();
        props.setProperty("wrapper.working.dir", home);
        props.setProperty("APP_NAME", process.getName());
        props.setProperty("APP_LONG_NAME", process.getDisplayName());
        props.setProperty("app.home", home);
        props.setProperty("app.name", process.getDisplayName());
        props.setProperty("app.port", process.getProperty("rmi.port"));
        props.setProperty("jmxremote.port", process.getProperty("jmx.port"));
        props.setProperty("address", process.getProperty("debug.port"));
        return createProperties(props, "wrapper.vars");
    }

    File nginxVars(ItsnowProcess process) {
        Properties props = new Properties();
        props.setProperty("instance", process.getName());
        props.setProperty("type", process.getAccount().getType());
        props.setProperty("host", process.getHostAddress());
        props.setProperty("port", process.getProperty("http.port"));
        props.setProperty("domain", process.getAccount().getDomain());
        return createProperties(props, "nginx.vars");
    }

    File createProperties(Properties props, String name) {
        File dumpFile = new File(System.getProperty("APP_HOME"), "tmp/" + name);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(dumpFile);
            PrintWriter writer = new PrintWriter(outputStream);
            props.list(writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't dump " + dumpFile.getName(), e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return dumpFile;
    }

}
