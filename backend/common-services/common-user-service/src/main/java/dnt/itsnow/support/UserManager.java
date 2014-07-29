/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.support;

import dnt.itsnow.model.User;
import dnt.itsnow.repository.UserRepository;
import dnt.itsnow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Basic User Service
 */
@Service
public class UserManager implements UserService {
    @Autowired
    UserRepository repository;

    PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

    @Override
    public User findUser(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Can't find user " + username);
        user.setAuthorities(repository.findAuthorities(username));
        return user;
    }

    @Override
    public boolean challenge(String username, String password) {
        User user = repository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Can't find user " + username);
        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * 对原始密码进行加密
     * @param rawPassword 原始密码
     * @return 被加密的密码
     */
    protected String encode(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }
}
