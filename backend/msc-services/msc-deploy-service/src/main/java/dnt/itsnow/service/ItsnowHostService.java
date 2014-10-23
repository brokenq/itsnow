/**
 * Developer: Kadvin Date: 14-9-15 上午10:45
 */
package dnt.itsnow.service;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.HostType;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;

import java.util.List;

/**
 * <h1>Itsnow Host Service</h1>
 */
public interface ItsnowHostService {

    /**
     * <h2>根据关键词查找主机</h2>
     *
     * @param keyword     主机的关键词，可以为null
     * @param pageRequest 分页设置
     * @return 查找的结果
     */
    Page<ItsnowHost> findAll(String keyword, PageRequest pageRequest);

    /**
     * <h2>查找到所有的数据库主机</h2>
     *
     * @return 数据库主机
     */
    List<ItsnowHost> findAllDbHosts();

    /**
     * <h2>根据地址查找主机</h2>
     *
     * @param address 主机地址
     * @return 主机对象，查不到则返回null
     */
    ItsnowHost findByAddress(String address);

    /**
     * <h2>根据name查找主机</h2>
     *
     * @param name 主机名称
     * @return 主机名称，查不到则返回null
     */
    ItsnowHost findByName(String name);

    /**
     * <h2>根据id查找主机 </h2>
     *
     * @param hostId 主机id
     * @return 主机对象，查不到则返回null
     */
    ItsnowHost findById(Long hostId);

    /**
     * <h2>根据name解析主机address </h2>
     *
     * @param name 主机名称
     * @return 主机地址
     */
    String resolveAddress(String name) throws ItsnowHostException;

    /**
     * <h2>根据address解析主机名 </h2>
     *
     * @param address 主机地址
     * @return 主机名
     */
    String resolveName(String address) throws ItsnowHostException;

    /**
     * <h2>创建主机</h2>
     *
     * @param creating 需要创建的主机对象
     * @return 创建好的主机对象
     * @throws ItsnowHostException
     */
    ItsnowHost create(ItsnowHost creating) throws ItsnowHostException;

    /**
     * <h2>删除主机</h2>
     *
     * @param host 被删除的主机
     * @throws ItsnowHostException
     */
    void delete(ItsnowHost host) throws ItsnowHostException;

    /**
     * <h2>读取一个主机相关任务的最新信息</h2>
     *
     * @param host   被读取的主机对象
     * @param job    正在/已经 执行的任务描述符
     * @param offset 信息的offset
     * @return 当前的位置，也是下次来读的始点
     */
    long follow(ItsnowHost host, String job, long offset, List<String> result);


    /**
     * <h2>更新主机</h2>
     * <p/>
     * 当前主要是更新主机的状态，以后可能有更新主机的普通属性以及地址
     *
     * @param host 被更新的主机
     * @throws ItsnowHostException
     */
    void update(ItsnowHost host) throws ItsnowHostException;

    /**
     * 为特定帐户 从当前的主机池中挑选出一个主机
     *
     *
     * @param account 帐户
     * @param type 主机类型
     * @return 可用的主机
     */
    ItsnowHost pickHost(Account account, HostType type) throws ItsnowHostException;

    /**
     * <h2>检查主机用户名密码是否有效</h2>
     *
     *  @param host 主机地址
     *  @param username 用户名
     *  @param password 密码
     *  throws ItsnowHostException
     */
    boolean checkPassword(String host, String username, String password) throws ItsnowHostException;

    /**
     * <h2>主机是否可删除</h2>
     * <p>如果主机有关联的进程或Schema，则不能删除，返回false；否则返回true</p>
     * @param host 主机对象
     * @return true：可删除；false：不可删除
     */
    boolean canDelete(ItsnowHost host);
}
