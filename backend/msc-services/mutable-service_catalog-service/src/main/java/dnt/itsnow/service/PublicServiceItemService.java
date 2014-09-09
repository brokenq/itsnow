/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.PublicServiceItem;

/**
 * <h1>公共服务项服务</h1>
 */
public interface PublicServiceItemService extends CommonServiceItemService {

    /**
     * <h2>保存新的公共服务项</h2>
     *
     * @param publicServiceItem 公共服务项对象
     * @return 新建的公共服务项对象
     */
    PublicServiceItem create(PublicServiceItem publicServiceItem);

    /**
     * <h2>更新公共服务项属性</h2>
     *
     * @param publicServiceItem 公共服务项对象
     * @return 更新后的公共服务项
     */
    PublicServiceItem update(PublicServiceItem publicServiceItem);

    /**
     * <h2>根据id删除公共服务项</h2>
     *
     * @param publicServiceItem 公共服务项对象
     */
    void delete(PublicServiceItem publicServiceItem);

    /**
     * <h2>新增帐户的公共服务项</h2>
     *
     * @param itemId 服务项ID
     * @param accountId 帐户ID
     */
    void saveByAccount(Long itemId,Long accountId);

    /**
     * <h2>删除帐户的公共服务项</h2>
     *
     * @param itemId 服务项ID
     * @param accountId 帐户ID
     */
    void deleteByAccount(Long itemId,Long accountId);

}
