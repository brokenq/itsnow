/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.support;

import dnt.itsnow.model.User;
import dnt.itsnow.repository.MutableUserRepository;
import dnt.itsnow.service.MutableUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * Mutable User Management
 */
@Service
public class MutableUserManager extends UserManager implements MutableUserService{
    private PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Autowired
    MutableUserRepository mutableService;

    @Override
    public void createUser(User user) {
        validatorFactory.getValidator().validate(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        mutableService.create(user);
    }

    @Override
    public void updateUser(User user) {
        validatorFactory.getValidator().validate(user);
        mutableService.update(user);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        mutableService.changePassword(user, passwordEncoder.encode(newPassword));
    }
}
