/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.PublicServiceItem;

/**
 * <h1>公共服务项服务</h1>
 */
public interface AccountServiceItemService {


    /**
     * <h2>新增帐户的公共服务项</h2>
     *
     * @param item 服务项
     * @param account 帐户
     */
    void createByAccount(PublicServiceItem item, Account account);

    /**
     * <h2>删除帐户的公共服务项</h2>
     *
     * @param item 服务项
     * @param account 帐户
     */
    void deleteByAccount(PublicServiceItem item, Account account);

}
