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
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
@Service
public class GroupManager extends Bean implements GroupService {

    @Autowired
    private GroupRepository repository;

    @Override
    public Page<Group> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding role by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = repository.count();
            List<Group> roles = repository.find("updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Group>(roles, pageable, total);
        }else{
            int total = repository.countByKeyword("%"+keyword+"%");
            List<Group> roles = repository.findByKeyword("%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Group>(roles, pageable, total);
        }
    }

    @Override
    public Group findBySn(String sn) {
        logger.debug("Finding Group by sn: {}", sn);

        return repository.findBySn(sn);
    }

    @Override
    public Group create(Group role) throws GroupException {
        logger.info("Creating role {}", role);
        if(role == null){
            throw new GroupException("Group entry can not be empty.");
        }
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
        repository.create(role);

        return role;
    }

    @Override
    public Group update(Group role) throws GroupException {
        logger.info("Updating role {}", role);
        if(role==null){
            throw new GroupException("Group entry can not be empty.");
        }
        repository.update(role);

        return role;
    }

    @Override
    public Group destroy(Group role) throws GroupException {
        logger.warn("Deleting role {}", role);
        if(role==null){
            throw new GroupException("Group entry can not be empty.");
        }
        repository.delete(role.getSn());
        return role;
    }

    @Override
    public List<Group> search(String keyword) {
        return repository.findAllByKeyword(keyword);
    }

    @Override
    public Group find(String name) {
        return repository.findByName(name);
    }
}
