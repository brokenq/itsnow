/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.PublicServiceCatalog;

/**
 * <h1>公共服务目录服务</h1>
 */
public interface PublicServiceCatalogService extends CommonServiceCatalogService {

    /**
     * <h2>保存新的公共服务目录</h2>
     *
     * @param publicServiceCatalog 公共服务目录对象
     * @return 新建的公共服务目录对象
     */
    PublicServiceCatalog create(PublicServiceCatalog publicServiceCatalog);

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
