/**
 * xiongjie on 14-7-24.
 */
package dnt.itsnow.support;

import dnt.itsnow.model.User;
import dnt.itsnow.repository.GroupRepository;
import dnt.itsnow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <h1>根据UserRepository加载用户的UserDetailsService</h1>
 */
@Service
public class GroupedUserDetailsService implements UserDetailsService {
    @Autowired
    UserService     userService;
    @Autowired
    GroupRepository groupRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUserByUsername(username);
        user.addAuthorities(groupRepository.findUserAuthorities(username));
        return user;
    }
}
