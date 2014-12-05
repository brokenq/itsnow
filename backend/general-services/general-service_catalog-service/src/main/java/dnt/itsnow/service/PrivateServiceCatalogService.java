package dnt.itsnow.service;

import dnt.itsnow.model.PrivateServiceCatalog;

import java.util.List;

/**
 * <h1>Private Service Catalog Service</h1>
 */
public interface PrivateServiceCatalogService extends CommonServiceCatalogService {

    /**
     * <h2>查询所有私有服务目录</h2>
     *
     * @return 私有服务目录列表
     */
    List<PrivateServiceCatalog> findAllPrivate();

    /**
     * <h2>根据SN查找私有服务目录</h2>
     *
     * @param sn 序列号
     * @return 找到的私有服务目录，找不到则返回null
     */
    PrivateServiceCatalog findPrivateBySn(String sn);

    /**
     * <h2>保存新的私有服务目录</h2>
     *
     * @param privateServiceCatalog 私有服务目录对象
     * @return 新建的私有服务目录对象
     */
    PrivateServiceCatalog savePrivate(PrivateServiceCatalog privateServiceCatalog);

    /**
     * <h2>更新新的私有服务目录</h2>
     *
     * @param privateServiceCatalog 私有服务目录对象
     * @return 更新的私有服务目录对象
     */
    PrivateServiceCatalog updatePrivate(PrivateServiceCatalog privateServiceCatalog);

    /**
     * <h2>根据SN删除私有服务目录</h2>
     *
     * @param catalog 删除的服务目录
     */
    void deletePrivate(PrivateServiceCatalog catalog);


    void setPrivateServiceCatalogList(List<PrivateServiceCatalog> privateServiceCatalogList);

    void setFormattedPrivateServiceCatalogList(List<PrivateServiceCatalog> formattedPrivateServiceCatalogList);

    /**
     * 根据title查找服务目录
     * @param title 服务目录title
     * @return 服务目录
     */
    PrivateServiceCatalog findPrivateByTitle(String title);
}
