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
    Page<ItsnowHost> findAll(String keyword, PageRequest pageRequest);

    /**
     * <h2>查找到所有的数据库主机</h2>
     *
     * @return 数据库主机
     */
    List<ItsnowHost> findAllDbHosts();

    ItsnowHost findByAddress(String address);

    ItsnowHost findById(Long hostId);

    ItsnowHost create(ItsnowHost creating) throws ItsnowHostException;

    void delete(ItsnowHost host) throws ItsnowHostException;

    /**
     * 读取一个主机相关任务的最新信息
     *
     * @param host    被读取的主机对象
     * @param job     正在/已经 执行的任务描述符
     * @param offset  信息的offset
     * @return 当前的位置，也是下次来读的始点
     */
    long follow(ItsnowHost host, String job, long offset, List<String> result);
}
