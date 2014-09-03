/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.PublicServiceCatalog;

import java.util.List;

/**
 * <h1>公共服务目录服务</h1>
 */
public interface PublicServiceCatalogService {

    /**
     * <h2>查询所有公共服务目录</h2>
     *
     * @return 公共服务目录列表
     */
    List<PublicServiceCatalog> findAll();

    /**
     * <h2>根据SN查找公共服务目录</h2>
     *
     * @param sn 序列号
     * @return 找到的公共服务目录，找不到则返回null
     */
    PublicServiceCatalog findBySn(String sn);

    /**
     * <h2>保存新的公共服务目录</h2>
     *
     * @param publicServiceCatalog 公共服务目录对象
     * @return 新建的公共服务目录对象
     */
    PublicServiceCatalog save(PublicServiceCatalog publicServiceCatalog);

    /**
     * <h2>更新公共服务目录属性</h2>
     *
     * @param publicServiceCatalog 公共服务目录对象
     * @return 更新后的公共服务目录
     */
    PublicServiceCatalog update(PublicServiceCatalog publicServiceCatalog);

    /**
     * <h2>根据SN删除公共服务目录</h2>
     *
     * @param sn 序列号
     */
    void delete(String sn);

}
