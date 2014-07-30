/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <h1>关于用户的服务</h1>
 */
public interface UserService extends UserDetailsService {

    User findUser(String username);

    User loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * 判断某个用户的密码是否正确
     *
     * @param username 被判断的用户名
     * @param password 被判断的密码
     * @return 是否正确
     */
    boolean challenge(String username, String password);
}
