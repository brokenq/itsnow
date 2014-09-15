package dnt.itsnow.support;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.GroupRepository;
import dnt.itsnow.service.GroupService;
import dnt.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public class GroupManager extends Bean implements GroupService {

    @Autowired
    private GroupRepository roleRepository;

    @Override
    public Page<Group> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding role by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = roleRepository.count();
            List<Group> roles = roleRepository.find("updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Group>(roles, pageable, total);
        }else{
            int total = roleRepository.countByKeyword("%"+keyword+"%");
            List<Group> roles = roleRepository.findByKeyword("%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Group>(roles, pageable, total);
        }
    }

    @Override
    public Group findBySn(String sn) {
        logger.debug("Finding Group by sn: {}", sn);

        return roleRepository.findBySn(sn);
    }

    @Override
    public Group create(Group role) throws GroupException {
        logger.info("Creating role {}", role);
        if(role == null){
            throw new GroupException("Group entry can not be empty.");
        }
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
        roleRepository.create(role);

        return role;
    }

    @Override
    public Group update(Group role) throws GroupException {
        logger.info("Updating role {}", role);
        if(role==null){
            throw new GroupException("Group entry can not be empty.");
        }
        roleRepository.update(role);

        return role;
    }

    @Override
    public Group destroy(Group role) throws GroupException {
        logger.warn("Deleting role {}", role);
        if(role==null){
            throw new GroupException("Group entry can not be empty.");
        }
        roleRepository.delete(role.getSn());
        return role;
    }
}
