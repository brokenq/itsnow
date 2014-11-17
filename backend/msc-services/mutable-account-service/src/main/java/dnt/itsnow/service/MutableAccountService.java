/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

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

    /**
     * <h2>实际注册帐户的接口</h2>
     *
     * @param account 注册的帐户信息
     * @param user    管理员信息
     */
    Account register(Account account, User user) throws AccountException;

    /**
     * <h2>查找所有 没有分配进程的账户</h2>
     */
    List<Account> findAllForNoProcess();

    /**
     * <h2>重置账号状态为New  由于注册功能问题，集成测试无法注册新账号，所以此处增加resetNew方法，注册功能解决后即删除</h2>
     */
    void resetNew(String sn) throws AccountException;
}
