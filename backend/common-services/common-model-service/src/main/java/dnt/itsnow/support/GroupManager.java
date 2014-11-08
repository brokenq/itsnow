package dnt.itsnow.support;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.GroupRepository;
import dnt.itsnow.service.GroupService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>组管理业务实现类</h1>
 */
@Service
public class GroupManager extends Bean implements GroupService {

    @Autowired
    private GroupRepository repository;

    @Override
    public Page<Group> findAll(String keyword, Pageable pageable) {

        logger.debug("Finding group by keyword: {}", keyword);

        int total = repository.count(keyword);
        List<Group> groups = new ArrayList<Group>();
        if (total > 0) {
            groups = repository.findAll(keyword, pageable);
        }
        DefaultPage<Group> page = new DefaultPage<Group>(groups, pageable, total);

        logger.debug("Found   {}", page);

        return page;
    }

    @Override
    public Group findByName(String name) {

        logger.debug("Finding group by name: {}", name);

        Group group = repository.findByName(name);

        logger.debug("Found   {}", group);

        return group;
    }

    @Override
    public Group create(Group group) throws GroupException {

        logger.info("Creating {}", group);

        if (group == null) {
            throw new GroupException("Group entry can not be empty.");
        }
        group.creating();
        repository.create(group);

        logger.info("Created  {}", group);

        return group;
    }

    @Override
    public Group update(Group group) throws GroupException {

        logger.info("Updating {}", group);

        if (group == null) {
            throw new GroupException("Group entry can not be empty.");
        }
        group.updating();
        repository.update(group);

        logger.info("Updated  {}", group);

        return group;
    }

    @Override
    public Group destroy(Group group) throws GroupException {

        logger.warn("Deleting {}", group);

        if (group == null) {
            throw new GroupException("Group entry can not be empty.");
        }
        repository.deleteGroupAuthority(group.getId());
        repository.deleteGroupMember(group.getId());
        repository.delete(group.getName());

        logger.warn("Deleted  {}", group);

        return group;
    }

}
