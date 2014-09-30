package dnt.itsnow.support;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.MscGroupRepository;
import dnt.itsnow.service.MscGroupService;
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
 * <h1>MSC组管理业务层</h1>
 */
@Service
public class MscGroupManager extends Bean implements MscGroupService {

    @Autowired
    @Qualifier("globalMessageBus")
    MessageBus globalMessageBus;

    @Autowired
    private MscGroupRepository repository;

    @Override
    public Page<Group> findAll(String keyword, PageRequest pageRequest) {

        logger.debug("Finding group by keyword: {}", keyword);

        if (StringUtils.isBlank(keyword)) {
            int total = repository.count();
            List<Group> groups = repository.findAll("updated_at", "desc", pageRequest.getOffset(), pageRequest.getPageSize());
            DefaultPage page = new DefaultPage<Group>(groups, pageRequest, total);

            logger.debug("Finded group:{}", page);

            return page;
        } else {
            int total = repository.countByKeyword("%" + keyword + "%");
            List<Group> groups = repository
                    .findAllByKeyword("%" + keyword + "%", "updated_at", "desc", pageRequest.getOffset(),
                            pageRequest.getPageSize());
            DefaultPage page = new DefaultPage<Group>(groups, pageRequest, total);

            logger.debug("Finded group:{}", page);

            return page;
        }
    }

    @Override
    public Page<Group> findAllRelevantInfo(String name, PageRequest pageRequest) {

        logger.debug("Finding group relationship info by name: {}", name);

        int total = repository.countByRelevantInfo(name);
        List<Group> groups = repository.findAllRelevantInfo(name, "updated_at", "desc", pageRequest.getOffset(), pageRequest.getPageSize());
        DefaultPage page = new DefaultPage<Group>(groups, pageRequest, total);

        logger.debug("Finded group relationship info:{}", page);

        return page;
    }


    @Override
    public Group findByName(String name) {

        logger.debug("Finding group by name:{}", name);

        Group group = repository.findByName(name);

        logger.debug("Finded group:{}", group);

        return group;
    }

    @Override
    public Group create(Group group) throws GroupException {

        logger.info("Creating group:{}", group);

        if (group == null) {
            throw new GroupException("MspGroup entry can not be empty.");
        }
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        group.setUpdatedAt(group.getCreatedAt());
        repository.create(group);

        logger.info("Created group:{}", group);

        globalMessageBus.publish("MscGroup", "+" + JsonSupport.toJSONString(group));

        return group;
    }

    @Override
    public Group update(Group group) throws GroupException {

        logger.info("Updating group {}", group);

        if (group == null) {
            throw new GroupException("MspGroup entry can not be empty.");
        }
        repository.update(group);

        logger.info("Updated group {}", group);

        globalMessageBus.publish("MscGroup", "*" + JsonSupport.toJSONString(group));

        return group;
    }

    @Override
    public void destroy(Group group) throws GroupException {

        logger.warn("Deleting group {}", group);

        if (group == null) {
            throw new GroupException("MspGroup entry can not be empty.");
        }
        repository.deleteGroupAndUserRelationByGroupName(group.getName());
        repository.delete(group.getName());

        logger.warn("Deleted group {}", group);

        globalMessageBus.publish("MscGroup", "-" + JsonSupport.toJSONString(group));
    }

    @Override
    public List<Group> search(String keyword) {
        return repository.searchAllByKeyword(keyword);
    }

}
