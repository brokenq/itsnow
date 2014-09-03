/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.PrivateServiceItem;

import java.util.List;

/**
 * <h1>公共服务项服务</h1>
 */
public interface PrivateServiceItemService extends CommonServiceItemService {

    /**
     * <h2>查询所有私有服务项</h2>
     *
     * @return 私有服务项列表
     */
    List<PrivateServiceItem> findAllPrivate();

    /**
     * <h2>根据id查找私有服务项<   *
     * @param id 编号
     * @return 找到的私有服务项，找不到则返回null
     */
    PrivateServiceItem findPrivateById(Long id);


    /**
     * <h2>保存新的私有服务项</h2>
     *
     * @param privateServiceItem 私有服务项对象
     * @return 新建的私有服务项对象
     */
    PrivateServiceItem savePrivate(PrivateServiceItem privateServiceItem);

    /**
     * <h2>根据id删除私有服务项</h2>
     *
     * @param id 编号
     */
    void deletePrivate(Long id);

}
