package dnt.itsnow.support;

import dnt.itsnow.exception.RoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.RoleRepository;
import dnt.itsnow.service.RoleService;
import dnt.messaging.MessageBus;
import dnt.spring.Bean;
import dnt.support.JsonSupport;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    public Page<Role> findAll(Long accountId, String keyword, PageRequest pageRequest) {

        logger.debug("Finding roles by keyword:{}, accountId:{}", keyword, accountId);

        if(StringUtils.isBlank(keyword)){
            int total = repository.count(accountId).intValue();
            List<Role> roles = repository.findAll(accountId, "updated_at", "desc", pageRequest.getOffset(), pageRequest.getPageSize());
            DefaultPage page = new DefaultPage<Role>(roles, pageRequest, total);

            logger.debug("Finded role list info:{}", page);

            return page;
        }else{
            int total = repository.countByKeyword(accountId, "%"+keyword+"%").intValue();
            List<Role> roles = repository.findAllByKeyword(accountId, "%" + keyword + "%", "updated_at", "desc", pageRequest.getOffset(), pageRequest.getPageSize());
            DefaultPage page = new DefaultPage<Role>(roles, pageRequest, total);

            logger.debug("Finded role list info:{}", page);

            return page;
        }
    }

    @Override
    public Page<Role> findAllRelevantInfo(String keyword, PageRequest pageRequest) {

        logger.debug("Finding roles by keyword:{}, paging info:{}", keyword, pageRequest);

        int total = repository.countByRelevantInfo(keyword).intValue();
        List<Role> roles = repository.findAllRelevantInfo(keyword, "updated_at", "desc", pageRequest.getOffset(), pageRequest.getPageSize());
        DefaultPage page = new DefaultPage<Role>(roles, pageRequest, total);

        logger.debug("Finded role list info:{}", page);

        return page;
    }

    @Override
    public Role findByName(String name) {

        logger.debug("Finding Role by name:{}", name);

        Role role = repository.findByName(name);

        logger.debug("Finded role:{}", role);

        return role;
    }

    @Override
    public Role create(Role role) throws RoleException {

        logger.info("Creating role:{}", role);

        if(role == null){
            throw new RoleException("Role entry can not be empty.");
        }
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
        repository.create(role);

        logger.info("Created role:{}", role);

        globalMessageBus.publish("Role", "+" + JsonSupport.toJSONString(role));

        return role;
    }

    @Override
    public Role update(Role role) throws RoleException {

        logger.info("Updating role:{}", role);

        if(role==null){
            throw new RoleException("Role entry can not be empty.");
        }

        repository.update(role);

        logger.info("Updated role");

        globalMessageBus.publish("Role", "*" + JsonSupport.toJSONString(role));

        return role;
    }

    @Override
    public void destroy(Role role) throws RoleException {

        logger.warn("Deleting role {}", role);

        if(role==null){
            throw new RoleException("Role entry can not be empty.");
        }

        repository.deleteRoleAndUserRelationByRoleName(role.getName());
        repository.deleteRoleAndGroupRelationByRoleName(role.getName());

        repository.delete(role.getName());

        globalMessageBus.publish("Role", "-" + JsonSupport.toJSONString(role));

        logger.warn("Deletd role");
    }

}
