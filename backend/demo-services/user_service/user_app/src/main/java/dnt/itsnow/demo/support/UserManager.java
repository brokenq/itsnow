/**
 * Developer: Kadvin Date: 14-7-14 下午3:27
 */
package dnt.itsnow.demo.support;

import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.demo.api.UserService;
import dnt.itsnow.demo.model.User;
import dnt.itsnow.demo.repository.UserRepository;
import dnt.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The user service implementation
 */
@Service
class UserManager extends Bean implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public User find(String username, String password) {
        return repository.findByNameAndPassword(username, password);
    }

    @Override
    public User find(String username) {
        return repository.findByName(username);
    }

    @Override
    public Page<User> findAll(String keyword, Pageable pageable) {
        if(StringUtils.isBlank(keyword)){
            int total = repository.count();
            List<User> users = repository.findUsers("updated_at desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<User>(users, pageable, total);
        }else{
            int total = repository.countByKeyword(keyword);
            List<User> users = repository.findUsersByKeyword(keyword, "updated_at desc",
                                                             pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<User>(users, pageable, total);
        }
    }
}
