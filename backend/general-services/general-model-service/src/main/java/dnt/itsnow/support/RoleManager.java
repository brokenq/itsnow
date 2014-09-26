package dnt.itsnow.support;

import dnt.itsnow.exception.RoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.RoleRepository;
import dnt.itsnow.service.RoleService;
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
public class RoleManager extends Bean implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Role> findAll(Long accountId, String keyword, Pageable pageable) {
        logger.debug("Finding role by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = roleRepository.count(accountId);
            List<Role> roles = roleRepository.findAll(accountId, "updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Role>(roles, pageable, total);
        }else{
            int total = roleRepository.countByKeyword(accountId, "%"+keyword+"%");
            List<Role> roles = roleRepository.findAllByKeyword(accountId, "%" + keyword + "%", "updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Role>(roles, pageable, total);
        }
    }

    @Override
    public Page<Role> findAllRelevantInfo(String keyword, Pageable pageable) {
        logger.debug("Manager Finding group by keyword: {}, Offset: {}, PageSize: {}", keyword, pageable.getOffset(), pageable.getPageSize());
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
    public Role create(Role role) throws RoleException {
        logger.info("Creating role {}", role);
        if(role == null){
            throw new RoleException("Role entry can not be empty.");
        }
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
        roleRepository.create(role);

        return role;
    }

    @Override
    public Role update(Role role) throws RoleException {
        logger.info("Updating role {}", role);
        if(role==null){
            throw new RoleException("Role entry can not be empty.");
        }
        roleRepository.update(role);

        return role;
    }

    @Override
    public Role destroy(Role role) throws RoleException {
        logger.warn("Deleting role {}", role);
        if(role==null){
            throw new RoleException("Role entry can not be empty.");
        }
        roleRepository.delete(role.getSn());
        return role;
    }

}
