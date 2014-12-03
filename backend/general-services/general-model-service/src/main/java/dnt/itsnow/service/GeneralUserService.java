package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.service.Pageable;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.web.model.ChangePasswordRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by User on 2014/11/10.
 */
public interface GeneralUserService {
    Page findAll(String keyword, Pageable pageRequest,Account mainAccount);

    User create(User user);

    void update(User user);

    User findByUsername(String username);

    void delete(User user);

    User findByEmail(String email);

    List<User> findUsersByAccount(Account mainAccount);

    void changePassword(ChangePasswordRequest changeRequest);
    boolean challenge(String username,String password);
}
