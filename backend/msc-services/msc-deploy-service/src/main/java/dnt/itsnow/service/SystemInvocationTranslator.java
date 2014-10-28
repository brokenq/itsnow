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
    String getAppDomain();
    /**
     * <h2>创建一个配置特定主机的系统调用对象</h2>
     *
     * @param host 刚刚建立的主机记录，该主机应该是系统运维人员刚刚配置好的机器(虚拟机)，上面尚未配置任何环境
     * @return 调用对象
     */
    SystemInvocation provision(ItsnowHost host);

    /**
     * <h2>创建一个主机下线的系统调用对象</h2>
     *
     * @param host 需要下线的主机
     * @return 调用对象
     */
    SystemInvocation delist(ItsnowHost host);

    /**
     * <h2>创建一个建立特定schema的系统调用对象</h2>
     *
     * @param schema 需要建立的schema，schema自身已经关联到在哪台主机上创建了
     * @return 调用对象
     */
    SystemInvocation create(ItsnowSchema schema);

    /**
     * <h2>创建一个销毁特定schema的系统调用对象</h2>
     *
     * @param schema 需要drop的schema，schema自身已经关联到在哪台主机上
     * @return 调用对象
     */
    SystemInvocation drop(ItsnowSchema schema);

    /**
     * <h2>创建一个部署特定系统的调用对象</h2>
     *
     * @param process 待部署的系统
     * @return 调用对象
     */
    SystemInvocation deploy(ItsnowProcess process);

    /**
     * <h2>创建一个取消部署特定系统的调用对象</h2>
     *
     * @param process 待取消部署的系统
     * @return 调用对象
     */
    SystemInvocation undeploy(ItsnowProcess process);

    /**
     * <h2>创建一个启动特定系统的调用对象</h2>
     *
     * @param process 待启动的系统
     * @return 调用对象
     */
    SystemInvocation start(ItsnowProcess process);

    /**
     * <h2>创建一个停止特定系统的调用对象</h2>
     *
     * @param process 待停止的系统
     * @return 调用对象
     */
    SystemInvocation stop(ItsnowProcess process);
    
    
    /**
     * <h2>创建一个停止特定系统的调用对象</h2>
     *
     * @param host 待检测的系统
     * @return 调用对象
     */
    SystemInvocation check(ItsnowHost host);
    
    /**
     * <h2>创建一个停止特定系统的调用对象</h2>
     *
     * @param process 待检测的服务
     * @return 调用对象
     */
    SystemInvocation check(ItsnowProcess process);

    /**
     * <h2>检查主机用户名密码</h2>
     *
     * @param host 主机地址
     * @param username 用户名
     * @param password 密码
     * @return 调用对象
     */
    SystemInvocation checkHostUser(String host, String username, String password);
}
