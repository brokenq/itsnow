package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by User on 2014/11/10.
 */
public interface GeneralUserService {
    Page findAll(String keyword, Pageable pageRequest,Account mainAccount);

    User create(User user);

    void update(User user);

    User findByUsername(String username);

    void delete(User user);

    void changePassword(String username, String newPassword);

    User findByEmail(String email);
}
