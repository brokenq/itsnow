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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * <h1>系统任务生成器</h1>
 *
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
                return process.run("trust_me.sh",
                                 host.getAddress(),
                                 host.getConfiguration().getProperty("user"),
                                 host.getConfiguration().getProperty("password")
                                );
            }
        }.next(new LocalInvocation() {
            public int perform(Process process) throws Exception {
                return process.run("prepare_host.sh", host.getAddress());
            }
        }).next(new RemoteInvocation(host.getAddress()) {
            public int perform(Process ssh) throws Exception {
                return ssh.run("provision.sh");
            }
        });
    }

    @Override
    public SystemInvocation delist(final ItsnowHost host) {
        return new RemoteInvocation(host.getAddress()) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("delist.sh");
            }
        }.next(new LocalInvocation() {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("delist_host.sh", host.getAddress());
            }
        });
    }

    @Override
    public SystemInvocation create(final ItsnowSchema schema) {
        return new RemoteInvocation(schema.getHostAddress()){
            @Override
            public int perform(Process process) throws Exception {
                return process.run("create_db.sh", schema.getName(),
                                 schema.getConfiguration().getProperty("user"),
                                 schema.getConfiguration().getProperty("password"));
            }
        };
    }

    @Override
    public SystemInvocation drop(final ItsnowSchema schema) {
        return new RemoteInvocation(schema.getHostAddress()){
            @Override
            public int perform(Process process) throws Exception {
                return process.run("drop_db.sh", schema.getName(),
                                 schema.getConfiguration().getProperty("user"));
            }
        };
    }

    @Override
    public SystemInvocation deploy(final ItsnowProcess itsnowProcess) {
        final String address = itsnowProcess.getHostAddress();
        final String targetPath = String.format("/opt/itsnow/%s/config/%s.properties",
                                                itsnowProcess.getName(), itsnowProcess.getName());
        return new RemoteInvocation(address){
            // 1. 生成需要部署的msc/msu的目录(做好各种link/配置文件copy工作)
            @Override
            public int perform(Process process) throws Exception {
                return process.run("prepare_deploy.sh",
                                   itsnowProcess.getAccount().getType().toLowerCase(),
                                   itsnowProcess.getName());
            }
        }.next(new LocalInvocation(){
            // 2. 把本地生成的该进程的配置文件通过scp copy过去
            @Override
            public int perform(Process process) throws Exception {
                //生成这个部署实体的属性文件
                File propFile = dumpProperties(itsnowProcess);
                String target = String.format("root@%s:%s", address, targetPath);
                return process.run("scp", propFile.getAbsolutePath(), target);
            }
        }).next(new RemoteInvocation(address) {
            //3. 实际开始部署
            @Override
            public int perform(Process process) throws Exception {
                return process.run("deploy.sh",
                                   itsnowProcess.getAccount().getType().toLowerCase(),
                                   itsnowProcess.getName(), targetPath);
            }
        });
    }

    @Override
    public SystemInvocation undeploy(final ItsnowProcess itsnowProcess) {
        return new RemoteInvocation(itsnowProcess.getHostAddress()) {
            // 暂时看到只需要远程执行一条语句
            //  卸载之前的备份等工作，暂时没有归在这里，而是通过外部工作
            // 而卸载之前的停止，清理等工作，也是需要外部执行(甚至应该是运营人员通过管理界面执行的）
            @Override
            public int perform(Process process) throws Exception {
                return process.run("delist.sh", itsnowProcess.getName());
            }
        };
    }

    @Override
    public SystemInvocation start(final ItsnowProcess itsnowProcess) {
        return new RemoteInvocation(itsnowProcess.getHostAddress()) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("start_ms.sh", itsnowProcess.getName());
            }
        };
    }

    @Override
    public SystemInvocation stop(final ItsnowProcess itsnowProcess) {
        return new RemoteInvocation(itsnowProcess.getHostAddress()) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("stop_ms.sh", itsnowProcess.getName());
            }
        };
    }

    File dumpProperties(ItsnowProcess process) {
        File dumpFile = new File(System.getProperty("APP_HOME"), "tmp/" + process.getName() + ".properties");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(dumpFile);
            PrintWriter writer = new PrintWriter(outputStream);
            Properties props = process.getConfiguration();
            props.list(writer);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't dump " + dumpFile.getName(), e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return dumpFile;
    }

}
