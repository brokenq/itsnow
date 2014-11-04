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
import dnt.messaging.MessageBus;
import dnt.support.JsonSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("globalMessageBus")
    MessageBus globalMessageBus;

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
        mutableRepository.create(user);
        logger.info("Created  {}", user);

        globalMessageBus.publish("User", "+" + JsonSupport.toJSONString(user));

        return user;
    }

    @Override
    public void update(User user) {
        logger.info("Updating {}", user);
        mutableRepository.update(user);
        logger.info("Updated  {}", user);
        globalMessageBus.publish("User", "*" + JsonSupport.toJSONString(user));
    }

    @Override
    public void changePassword(String username, String newPassword) {
        logger.info("Changing password for {}", username);
        mutableRepository.changePassword(username, encode(newPassword));
        logger.info("Changed  password for {}", username);
    }

    @Override
    public void deleteByAccountId(Long accountId) {
        logger.warn("Deleting all users of account with id = {}", accountId);
        mutableRepository.deleteAllByAccountId(accountId);
        logger.warn("Deleted  all users of account with id = {}", accountId);
    }

    @Override
    public User findByUsername(String username) {
        logger.info("find User by {}", username);
       return mutableRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        logger.info("find User by {}", email);
        return  mutableRepository.findByEmail(email);
    }
    @Override
    public void delete(User user) {
          mutableRepository.delete(user);
    }

}
