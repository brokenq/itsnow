/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.PublicServiceCatalog;

/**
 * <h1>公共服务目录服务</h1>
 */
public interface AccountServiceCatalogService {


    /**
     * <h2>新增帐户的公共服务目录</h2>
     *
     * @param catalog 服务目录
     * @param account 帐户
     */
    void createByAccount(PublicServiceCatalog catalog, Account account);

    /**
     * <h2>删除帐户的公共服务目录</h2>
     *
     * @param catalog 服务目录
     * @param account 帐户
     */
    void deleteByAccount(PublicServiceCatalog catalog, Account account);

}
