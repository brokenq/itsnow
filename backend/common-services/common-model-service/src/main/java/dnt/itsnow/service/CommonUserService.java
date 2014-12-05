/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
/** <h1>关于用户的服务</h1> */
public interface CommonUserService extends UserDetailsService {

    /**
     * <h2>根据用户名在当前系统的帐户下查找用户</h2>
     *
     * @param username 用户名
     * @return 找到的用户 不包括权限等信息, 找不到则返回null
     */
    User findByUsername(String username);

    /**
     * <h2>根据email查找用户</h2>
     *
     * @param email 用户邮件地址
     * @return 找到的用户 不包括权限等信息, 找不到则返回null
     */
    User findByEmail(String email);

    /**
     * <h2>根据电话查找用户</h2>
     *
     * @param phone 用户电话
     * @return 找到的用户 不包括权限等信息, 找不到则返回null
     */
    User findByPhone(String phone);

    /**
     * 加载在当前系统的帐户下的用户各种信息
     *
     * @param username 用户名
     * @return 包括了权限等信息的用户详情
     * @throws UsernameNotFoundException 用户名不存在时抛出该异常
     */
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * <h2>判断某个在当前系统的帐户下的用户的密码是否正确</h2>
     *
     * @param username 被判断的用户名
     * @param password 被判断的密码
     * @return 是否正确
     */
    boolean challenge(String username, String password);

    Account findAccountById(Long accountId);

    /**
     *
     * @param mainAccount
     * @return
     */
    List<User> findUsersByAccount(Account mainAccount);

    /**
     * 根据合同查询相关的MSP用户
     * @param mainAccount MSU账户
     * @return MSP用户列表
     */
    List<User> findAllByContract(Account mainAccount);

    /**
     * 查询指定账户下所有用户和MSU系统中与MSP签订合同的MSP用户
     * @param mainAccount 账户
     * @return 用户列表
     */
    List<User> findAllByAccountAndContract(Account mainAccount);

    /**
     * 查询所有可登录MSU系统的MSP用户
     * @param accountId MSU账户
     * @return MSP用户
     */
    List<User> findAllByLogOnContract(Long accountId);

}
