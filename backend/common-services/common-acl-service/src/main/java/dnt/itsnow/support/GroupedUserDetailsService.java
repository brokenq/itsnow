/**
 * xiongjie on 14-7-24.
 */
package dnt.itsnow.support;

import dnt.itsnow.model.User;
import dnt.itsnow.repository.GroupRepository;
import dnt.itsnow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <h1>根据UserRepository加载用户的UserDetailsService</h1>
 */
@Service("groupedUserService")
public class GroupedUserDetailsService implements UserService, UserDetailsService {
    @Autowired
    @Qualifier("origin") // 代码中的qualifier 就是 context 中的bean名称或id
    UserService     userService;
    @Autowired
    GroupRepository groupRepository;

    @Override
    public User findUser(String username) {
        return userService.findUser(username);
    }

    @Override
    public boolean challenge(String username, String password) {
        return userService.challenge(username, password);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUserByUsername(username);
        user.addAuthorities(groupRepository.findUserAuthorities(username));
        return user;
    }
}
