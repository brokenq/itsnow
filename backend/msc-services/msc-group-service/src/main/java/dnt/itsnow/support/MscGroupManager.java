package dnt.itsnow.support;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
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
 * <h1>类功能说明</h1>
 */
@Service
public class MscGroupManager extends Bean implements MscGroupService {

    @Autowired
    @Qualifier("globalMessageBus")
    MessageBus globalMessageBus;

    @Autowired
    private MscGroupRepository repository;

    @Override
    public Page<Group> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding group by keyword: {}", keyword);
        if (StringUtils.isBlank(keyword)) {
            int total = repository.count();
            List<Group> groups = repository.findAll("updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Group>(groups, pageable, total);
        } else {
            int total = repository.countByKeyword("%" + keyword + "%");
            List<Group> groups = repository
                    .findAllByKeyword("%" + keyword + "%", "updated_at", "desc", pageable.getOffset(),
                                      pageable.getPageSize());
            return new DefaultPage<Group>(groups, pageable, total);
        }
    }

    @Override
    public Page<Group> findAllRelevantInfo(String keyword, Pageable pageable) {
        logger.debug("Manager Finding group by keyword: {}", keyword);
        int total = repository.countByRelevantInfo("%" + keyword + "%");
        List<Group> groups = repository.findAllRelevantInfo("%" + keyword + "%", "updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
        logger.debug("Manager Finded group by keyword: {}, size is {}", keyword, groups.size());
        return new DefaultPage<Group>(groups, pageable, total);
    }


    @Override
    public Group findBySn(String sn) {
        logger.debug("Finding MspGroup by sn: {}", sn);

        return repository.findBySn(sn);
    }

    @Override
    public Group create(Group group) throws GroupException {
        logger.info("Creating group {}", group);
        if(group == null){
            throw new GroupException("MspGroup entry can not be empty.");
        }
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        group.setUpdatedAt(group.getCreatedAt());
        repository.create(group);
        globalMessageBus.publish("MscGroup", "+" + JsonSupport.toJSONString(group));
        return group;
    }

    @Override
    public Group update(Group group) throws GroupException {
        logger.info("Updating group {}", group);
        if(group==null){
            throw new GroupException("MspGroup entry can not be empty.");
        }
        repository.update(group);
        globalMessageBus.publish("MscGroup", "*" + JsonSupport.toJSONString(group));

        return group;
    }

    @Override
    public Group destroy(Group group) throws GroupException {
        logger.warn("Deleting group {}", group);
        if(group==null){
            throw new GroupException("MspGroup entry can not be empty.");
        }

        repository.deleteGroupAndUserRelationByGroupName(group.getName());
        repository.delete(group.getSn());

        globalMessageBus.publish("MscGroup", "-" + JsonSupport.toJSONString(group));
        return group;
    }

    @Override
    public List<Group> search(String keyword) {
        return repository.searchAllByKeyword(keyword);
    }

    @Override
    public Group find(String name) {
        return repository.findByName(name);
    }
}
