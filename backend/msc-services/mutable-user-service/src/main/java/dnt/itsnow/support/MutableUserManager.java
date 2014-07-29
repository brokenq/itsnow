/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.support;

import dnt.itsnow.model.User;
import dnt.itsnow.repository.MutableUserRepository;
import dnt.itsnow.service.MutableUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;

/**
 * Mutable User Management
 */
@Service
public class MutableUserManager extends UserManager
        /* 当 UserManager 被声明为 package visible 时，
           由于跨了class loader，
           虽然 Mutable User Manager与User Manager在同一个包下面，
           但继承时依然会抛出 IllegalAccessException，
           这是底层component-framework的class loading机制需要解决的一个典型问题*/
        implements MutableUserService{
    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Autowired
    MutableUserRepository mutableRepository;

    @Override
    public User createUser(User user) {
        validatorFactory.getValidator().validate(user);
        user.setPassword(encode(user.getPassword()));
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(user.getCreatedAt());
        long id = mutableRepository.create(user);
        user.setId(id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        validatorFactory.getValidator().validate(user);
        mutableRepository.update(user);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        mutableRepository.changePassword(username, encode(newPassword));
    }
}
