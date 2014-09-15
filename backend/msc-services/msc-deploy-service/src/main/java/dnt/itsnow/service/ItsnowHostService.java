/**
 * Developer: Kadvin Date: 14-9-15 上午10:45
 */
package dnt.itsnow.service;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;

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
    Page<ItsnowHost> findAllDbHosts();

    ItsnowHost findByAddress(String address);

    ItsnowHost findById(Integer hostId);

    ItsnowHost create(ItsnowHost creating) throws ItsnowHostException;

    void delete(String address) throws ItsnowHostException;
}
