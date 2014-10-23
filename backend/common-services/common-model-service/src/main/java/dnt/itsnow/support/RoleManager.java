package dnt.itsnow.support;

import dnt.itsnow.exception.RoleException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.Role;
import dnt.itsnow.model.User;
import dnt.itsnow.model.UserAuthority;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.RoleRepository;
import dnt.itsnow.service.RoleService;
import dnt.messaging.MessageBus;
import dnt.spring.Bean;
import dnt.support.JsonSupport;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>角色管理业务层</h1>
 */
@Service
public class RoleManager extends Bean implements RoleService {

    @Autowired
    @Qualifier("globalMessageBus")
    MessageBus globalMessageBus;

    @Autowired
    private RoleRepository repository;

    @Override
    public Page<Role> findAll(String keyword, Pageable pageable) {

        logger.debug("Finding roles by keyword:{}", keyword);

        int total = repository.count(keyword);
        List<Role> roles = new ArrayList<Role>();
        if (total > 0) {
            roles = repository.findAll(keyword, pageable);
        }
        DefaultPage<Role> page = new DefaultPage<Role>(roles, pageable, total);

        logger.debug("Found   {}", page);

        return page;
    }

    @Override
    public Role findByName(String name) {

        logger.debug("Finding role by name:{}", name);

        Role role = repository.findByName(name);

        logger.debug("Found   {}", role);

        return role;
    }

    @Override
    public Role create(Role role) throws RoleException {

        logger.info("Creating {}", role);

        if (role == null) {
            throw new RoleException("Role entry can not be empty.");
        }

        role.creating();
        repository.create(role);

        if (role.getUsers() != null) {
            UserAuthority userAuthority;
            for (User user : role.getUsers()) {
                userAuthority = new UserAuthority();
                userAuthority.setAuthority(role.getName());
                userAuthority.setUsername(user.getUsername());
                repository.createRoleAndUserRelation(userAuthority);
            }
        }

        logger.info("Created  {}", role);

        globalMessageBus.publish("Role", "+" + JsonSupport.toJSONString(role));

        return role;
    }

    @Override
    public Role update(Role role) throws RoleException {

        logger.info("Updating {}", role);

        if (role == null) {
            throw new RoleException("Role entry can not be empty.");
        }
        role.updating();
        repository.update(role);

        if (role.getUsers() != null) {
            repository.deleteRoleAndUserRelationByRoleName(role.getName());
            UserAuthority userAuthority;
            for (User user : role.getUsers()) {
                userAuthority = new UserAuthority();
                userAuthority.setAuthority(role.getName());
                userAuthority.setUsername(user.getUsername());
                repository.createRoleAndUserRelation(userAuthority);
            }
        }

        logger.info("Updated  {}", role);

        globalMessageBus.publish("Role", "*" + JsonSupport.toJSONString(role));

        return role;
    }

    @Override
    public void destroy(Role role) throws RoleException {

        logger.warn("Deleting {}", role);

        if (role == null) {
            throw new RoleException("Role entry can not be empty.");
        }

        repository.deleteRoleAndUserRelationByRoleName(role.getName());
        repository.deleteRoleAndGroupRelationByRoleName(role.getName());

        repository.delete(role.getName());

        globalMessageBus.publish("Role", "-" + JsonSupport.toJSONString(role));

        logger.warn("Deleted  {}", role);
    }

    @Override
    public List<User> findUsersByAccount(Account mainAccount) {

        logger.debug("Finding users by account:{}", mainAccount);

        List<User> users = repository.findUsersByAccountId(mainAccount.getId());

        logger.debug("Found   {}", users);

        return users;
    }

}
