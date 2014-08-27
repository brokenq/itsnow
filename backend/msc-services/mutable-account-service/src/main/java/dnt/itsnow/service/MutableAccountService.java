/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.model.Account;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>操控账户的服务</h1>
 */
public interface MutableAccountService extends CommonAccountService {
    Page<Account> findAll(String type, Pageable pageable);

    Account create(Account account);

    Account update(Account account) throws AccountException;

    void delete(Account account) throws AccountException;

    Account approve(Account account) throws AccountException;

    Account reject(Account account) throws AccountException;
}
