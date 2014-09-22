/**
 * Developer: Kadvin Date: 14-9-18 上午8:30
 */
package dnt.itsnow.service;

import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.model.SystemInvocation;

/**
 * <h1>System Invocation Translator Service</h1>
 */
public interface SystemInvocationTranslator {
    /**
     * <h2>创建一个配置特定主机的系统任务</h2>
     *
     * @param host 刚刚建立的主机记录，该主机应该是系统运维人员刚刚配置好的机器(虚拟机)，上面尚未配置任何环境
     * @return 任务
     */
    SystemInvocation provision(ItsnowHost host);

    /**
     * <h2>创建一个主机下线的系统任务</h2>
     *
     * @param host 需要下线的主机
     * @return 任务
     */
    SystemInvocation delist(ItsnowHost host);

    /**
     * <h2>创建一个建立特定schema的系统任务</h2>
     *
     * @param schema 需要建立的schema，schema自身已经关联到在哪台主机上创建了
     * @return 任务
     */
    SystemInvocation create(ItsnowSchema schema);

    /**
     * <h2>创建一个销毁特定schema的系统任务</h2>
     *
     * @param schema 需要drop的schema，schema自身已经关联到在哪台主机上
     * @return 任务
     */
    SystemInvocation drop(ItsnowSchema schema);

    /**
     * <h2>创建一个部署特定系统的任务</h2>
     *
     * @param process 待部署的系统
     * @return 任务
     */
    SystemInvocation deploy(ItsnowProcess process);

    /**
     * <h2>创建一个取消部署特定系统的任务</h2>
     *
     * @param process 待取消部署的系统
     * @return 任务
     */
    SystemInvocation undeploy(ItsnowProcess process);

    /**
     * <h2>创建一个启动特定系统的任务</h2>
     *
     * @param process 待启动的系统
     * @return 任务
     */
    SystemInvocation start(ItsnowProcess process);

    /**
     * <h2>创建一个停止特定系统的任务</h2>
     *
     * @param process 待停止的系统
     * @return 任务
     */
    SystemInvocation stop(ItsnowProcess process);
}
