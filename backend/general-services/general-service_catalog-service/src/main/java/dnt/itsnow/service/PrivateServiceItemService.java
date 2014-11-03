/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.PrivateServiceItem;

import java.util.List;

/**
 * <h1>Private Service Item Service</h1>
 */
public interface PrivateServiceItemService extends CommonServiceItemService {

    /**
     * <h2>查询所有私有服务项</h2>
     *
     * @return 私有服务项列表
     */
    List<PrivateServiceItem> findAllPrivate();

    /**
     * <h2>根据sn查找私有服务项<   *
     * @param sn 编号
     * @return 找到的私有服务项，找不到则返回null
     */
    PrivateServiceItem findPrivateBySn(String sn);


    /**
     * <h2>保存新的私有服务项</h2>
     *
     * @param privateServiceItem 私有服务项对象
     * @return 新建的私有服务项对象
     */
    PrivateServiceItem savePrivate(PrivateServiceItem privateServiceItem);

    /**
     * <h2>更新新的私有服务项</h2>
     *
     * @param privateServiceItem 私有服务项对象
     * @return 更新的私有服务项对象
     */
    PrivateServiceItem updatePrivate(PrivateServiceItem privateServiceItem);

    /**
     * <h2>根据sn删除私有服务项</h2>
     *
     * @param sn 编号
     */
    void deletePrivate(String sn);

}
