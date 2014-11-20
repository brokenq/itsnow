package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.PageRequest;
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
}
