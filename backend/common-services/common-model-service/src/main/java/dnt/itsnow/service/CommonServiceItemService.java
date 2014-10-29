/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.PublicServiceItem;

import java.util.List;

/**
 * <h1>通用服务目录项</h1>
 */
public interface CommonServiceItemService {

    /**
     * <h2>查询所有公共服务项</h2>
     *
     * @return 公共服务项列表
     */
    List<PublicServiceItem> findAll();

    /**
     * <h2>查询服务目录下的所有服务项</h2>
     *
     * @return 公共服务项列表
     */
    List<PublicServiceItem> findAllByCatalogSn(String sn);

    /**
     * <h2>根据SN查找公共服务项</h2>
     *
     * @param sn 序列号
     * @return 找到的公共服务项，找不到则返回null
     */
    PublicServiceItem findBySn(String sn);

    /**
     * <h2>根据帐户ID查找该帐户的公共服务项</h2>
     *
     * @param accountId 帐户编号
     * @return 找到的该帐户的所有公共服务项列表，找不到则返回null
     */
    List<PublicServiceItem> findByAccountId(Long accountId);


}
