package dnt.itsnow.support;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import net.happyonroad.platform.service.Pageable;
import net.happyonroad.platform.util.DefaultPage;
import net.happyonroad.platform.service.Page;
import dnt.itsnow.repository.GeneralUserRespository;
import dnt.itsnow.service.GeneralUserService;
import dnt.itsnow.web.model.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;

/**
 * Created by User on 2014/11/10.
 */
@Service
public class GeneralUserManager extends CommonUserManager implements GeneralUserService{
    @Autowired
    GeneralUserRespository generalUserRespository;
    @Autowired
    @Qualifier("mscRestTemplate")
    RestOperations facade;
    @Override
    public Page findAll(String keyword, Pageable pageable,Account mainAccount) {
        long total = generalUserRespository.count(keyword,mainAccount);
        List<User> users = generalUserRespository.findAll(keyword, pageable, mainAccount);
        return new DefaultPage<User>(users, pageable, total);
    }
    @Override
    public User create(User user) {
        logger.info("Creating {}", user);
        facade.postForEntity("/admin/api/users", user, User.class);
        return user;
    }

    @Override
    public void changePassword(ChangePasswordRequest changeRequest) {
         logger.info("Updating {}",changeRequest);
        facade.put("/api/password/change",changeRequest);
    }

    @Override
    public void update(User user) {
        logger.info("Updating {}", user);
        facade.put("/admin/api/users/{username}",
                user, user.getUsername());
        logger.info("Updated  {}", user);
    }
    @Override
    public void delete(User user) {
       logger.info("delete{}",user);
        facade.delete("/admin/api/users/{username}",user.getUsername());
       logger.info("delete{}",user);
    }


    @Override
    public List<User> findUsersByAccount(Account mainAccount) {
        return generalUserRespository.findUsersByAccount(mainAccount);
    }
}
