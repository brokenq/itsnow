/**
 * Developer: Kadvin Date: 14-9-15 上午10:45
 */
package dnt.itsnow.service;

import dnt.itsnow.exception.ItsnowHostException;
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
     * @param keyword  主机的关键词，可以为null
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
     * @param address 主机地址
     * @return 主机对象，查不到则返回null
     */
    ItsnowHost findByAddress(String address);

    /**
     * <h2>根据id查找主机 </h2>
     * @param hostId 主机id
     * @return 主机对象，查不到则返回null
     */
    ItsnowHost findById(Long hostId);

    /**
     * <h2>创建主机</h2>
     * @param creating  需要创建的主机对象
     * @return 创建好的主机对象
     * @throws ItsnowHostException
     */
    ItsnowHost create(ItsnowHost creating) throws ItsnowHostException;

    /**
     * <h2>删除主机</h2>
     * @param host  被删除的主机
     * @throws ItsnowHostException
     */
    void delete(ItsnowHost host) throws ItsnowHostException;

    /**
     * <h2>读取一个主机相关任务的最新信息</h2>
     *
     * @param host    被读取的主机对象
     * @param job     正在/已经 执行的任务描述符
     * @param offset  信息的offset
     * @return 当前的位置，也是下次来读的始点
     */
    long follow(ItsnowHost host, String job, long offset, List<String> result);


    /**
     * <h2>更新主机</h2>
     *
     * 当前主要是更新主机的状态，以后可能有更新主机的普通属性以及地址
     *
     * @param host  被更新的主机
     * @throws ItsnowHostException
     */
    void update(ItsnowHost host) throws ItsnowHostException;
}
