/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** <h1>关于用户的服务</h1> */
public interface CommonUserService extends UserDetailsService {

    /**
     * <h2>根据用户名查找用户</h2>
     *
     * @param username 用户名
     * @return 找到的用户 不包括权限等信息, 找不到则返回null
     */
    User findByUsername(String username);

    /**
     * 加载用户各种信息
     *
     * @param username 用户名
     * @return 包括了权限等信息的用户详情
     * @throws UsernameNotFoundException 用户名不存在时抛出该异常
     */
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * <h2>判断某个用户的密码是否正确</h2>
     *
     * @param username 被判断的用户名
     * @param password 被判断的密码
     * @return 是否正确
     */
    boolean challenge(String username, String password);

    Account findAccountById(Long accountId);
}
