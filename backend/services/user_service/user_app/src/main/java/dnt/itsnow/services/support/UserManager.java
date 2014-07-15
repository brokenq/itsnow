/**
 * Developer: Kadvin Date: 14-7-14 下午3:27
 */
package dnt.itsnow.services.support;

import dnt.itsnow.services.api.UserService;
import dnt.itsnow.services.model.User;
import dnt.itsnow.services.repository.UserRepository;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The user service implementation
 */
@Service
public class UserManager extends Bean implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public User find(String username, String password) {
        return repository.findByNameAndPassword(username, password);
    }
}
