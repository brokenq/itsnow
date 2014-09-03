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
    PublicServiceItem save(PublicServiceItem publicServiceItem);

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
     * @param id 编号
     */
    void delete(Long id);

}
