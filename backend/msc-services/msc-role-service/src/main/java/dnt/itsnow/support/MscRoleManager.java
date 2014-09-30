package dnt.itsnow.support;

import dnt.itsnow.exception.MscRoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.MscRoleRepository;
import dnt.itsnow.service.MscRoleService;
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
 * <h1>MSC角色管理业务层</h1>
 */
@Service
public class MscRoleManager extends Bean implements MscRoleService {

    @Autowired
    @Qualifier("globalMessageBus")
    MessageBus globalMessageBus;

    @Autowired
    private MscRoleRepository repository;

    @Override
    public Page<Role> findAll(String keyword, PageRequest pageRequest) {

        logger.debug("Finding role by keyword:{}", keyword);

        if(StringUtils.isBlank(keyword)){
            int total = repository.count();
            List<Role> roles = repository.findAll("updated_at", "desc", pageRequest.getOffset(), pageRequest.getPageSize());
            DefaultPage page = new DefaultPage<Role>(roles, pageRequest, total);

            logger.debug("Finded role list info:{}", page);

            return page;
        }else{
            int total = repository.countByKeyword("%"+keyword+"%");
            List<Role> roles = repository.findAllByKeyword("%" + keyword + "%", "updated_at", "desc", pageRequest.getOffset(), pageRequest.getPageSize());
            DefaultPage page = new DefaultPage<Role>(roles, pageRequest, total);

            logger.debug("Finded role list info:{}", page);

            return page;
        }
    }

    @Override
    public Page<Role> findAllRelevantInfo(String name, PageRequest pageRequest) {

        logger.debug("Finding roles by name:{}, paging info:{}", name, pageRequest);

        int total = repository.countByRelevantInfo(name);
        List<Role> roles = repository.findAllRelevantInfo(name, "updated_at", "desc", pageRequest.getOffset(), pageRequest.getPageSize());
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
    public Role create(Role role) throws MscRoleException {

        logger.info("Creating role {}", role);

        if(role == null){
            throw new MscRoleException("Role entry can not be empty.");
        }
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
        repository.create(role);

        logger.info("Created role:{}", role);

        globalMessageBus.publish("MscRole", "+" + JsonSupport.toJSONString(role));

        return role;
    }

    @Override
    public Role update(Role role) throws MscRoleException {

        logger.info("Updating role {}", role);

        if(role==null){
            throw new MscRoleException("Role entry can not be empty.");
        }
        repository.update(role);

        logger.info("Updated role");

        globalMessageBus.publish("MscRole", "*" + JsonSupport.toJSONString(role));

        return role;
    }

    @Override
    public void destroy(Role role) throws MscRoleException {

        logger.warn("Deleting role {}", role);

        if(role==null){
            throw new MscRoleException("Role entry can not be empty.");
        }

        repository.delete(role.getName());

        globalMessageBus.publish("MscRole", "-" + JsonSupport.toJSONString(role));

        logger.warn("Deletd role");
    }

}
