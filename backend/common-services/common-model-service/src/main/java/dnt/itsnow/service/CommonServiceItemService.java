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
     * <h2>根据id查找公共服务项</h2>
     *
     * @param id 编号
     * @return 找到的公共服务项，找不到则返回null
     */
    PublicServiceItem findById(Long id);


}