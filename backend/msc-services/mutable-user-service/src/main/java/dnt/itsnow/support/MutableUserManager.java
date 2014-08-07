/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.support;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.repository.MutableUserRepository;
import dnt.itsnow.service.MutableUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Mutable User Management
 */
@Service
public class MutableUserManager extends CommonUserManager
        /* 当 UserManager 被声明为 package visible 时，
           由于跨了class loader，
           虽然 Mutable User Manager与User Manager在同一个包下面，
           但继承时依然会抛出 IllegalAccessException，
           这是底层component-framework的class loading机制需要解决的一个典型问题*/
        implements MutableUserService{

    @Autowired
    MutableUserRepository mutableRepository;

    @Override
    public Page<User> findAll(String keyword, Pageable pageable) {
        long total = mutableRepository.countByKeyword(keyword);
        List<User> users = mutableRepository.findAllByKeyword(keyword, pageable);
        return new DefaultPage<User>(users, pageable, total);
    }

    @Override
    public User create(User user) {
        logger.info("Creating {}", user);
        user.setPassword(encode(user.getPassword()));
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(user.getCreatedAt());
        long id = mutableRepository.create(user);
        user.setId(id);
        logger.info("Created  {}", user);
        return user;
    }

    @Override
    public void update(User user) {
        logger.info("Updating {}", user);
        mutableRepository.update(user);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        mutableRepository.changePassword(username, encode(newPassword));
    }
}
