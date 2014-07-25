/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.support;

import dnt.itsnow.model.User;
import dnt.itsnow.repository.UserRepository;
import dnt.itsnow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Basic User Service
 */
public class UserManager implements UserService {
    @Autowired
    UserRepository  userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Can't find user " + username);
        user.setAuthorities(userRepository.findAuthorities(username));
        return user;
    }
}
