package dnt.itsnow.support;

import dnt.itsnow.exception.MscRoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.CommonUserRepository;
import dnt.itsnow.repository.MscRoleRepository;
import dnt.itsnow.service.MscRoleService;
import dnt.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
@Service
public class MscRoleManager extends Bean implements MscRoleService {

    @Autowired
    private MscRoleRepository roleRepository;

    @Override
    public Page<Role> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding role by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = roleRepository.count();
            List<Role> roles = roleRepository.findAll("updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Role>(roles, pageable, total);
        }else{
            int total = roleRepository.countByKeyword("%"+keyword+"%");
            List<Role> roles = roleRepository.findAllByKeyword("%" + keyword + "%", "updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Role>(roles, pageable, total);
        }
    }

    @Override
    public Page<Role> findAllRelevantInfo(String keyword, Pageable pageable) {
        logger.debug("Manager Finding group by keyword: {}", keyword);
        int total = roleRepository.countByRelevantInfo("%" + keyword + "%");
        List<Role> roles = roleRepository.findAllRelevantInfo("%" + keyword + "%", "updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
        logger.debug("Manager Finded group by keyword: {}, size is {}", keyword, roles.size());
        return new DefaultPage<Role>(roles, pageable, total);
    }

    @Override
    public Role findByName(String name) {
        logger.debug("Finding Role by name: {}", name);

        return roleRepository.findByName(name);
    }

    @Override
    public Role create(Role role) throws MscRoleException {
        logger.info("Creating role {}", role);
        if(role == null){
            throw new MscRoleException("Role entry can not be empty.");
        }
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
        roleRepository.create(role);

        return role;
    }

    @Override
    public Role update(Role role) throws MscRoleException {
        logger.info("Updating role {}", role);
        if(role==null){
            throw new MscRoleException("Role entry can not be empty.");
        }
        roleRepository.update(role);

        return role;
    }

    @Override
    public Role destroy(Role role) throws MscRoleException {
        logger.warn("Deleting role {}", role);
        if(role==null){
            throw new MscRoleException("Role entry can not be empty.");
        }
        roleRepository.delete(role.getSn());
        return role;
    }

}
