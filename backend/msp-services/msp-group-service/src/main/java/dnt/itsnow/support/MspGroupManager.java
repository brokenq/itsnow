package dnt.itsnow.support;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.MspGroup;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.GroupRepository;
import dnt.itsnow.repository.MspGroupRepository;
import dnt.itsnow.serivce.MspGroupService;
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
public class MspGroupManager extends Bean implements MspGroupService {

    @Autowired
    private MspGroupRepository repository;

    @Override
    public Page<MspGroup> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding role by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = repository.count();
            List<MspGroup> roles = repository.findAll("updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<MspGroup>(roles, pageable, total);
        }else{
            int total = repository.countByKeyword("%"+keyword+"%");
            List<MspGroup> groups = repository.findAllByKeyword("%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<MspGroup>(groups, pageable, total);
        }
    }

    @Override
    public Page<MspGroup> findAllRelevantInfo(String keyword, Pageable pageable) {
        logger.debug("Finding role by keyword: {}", keyword);
        int total = repository.countByKeyword("%" + keyword + "%");
        List<MspGroup> groups = repository.findAllRelevantInfo("%" + keyword + "%", "updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
        return new DefaultPage<MspGroup>(groups, pageable, total);
    }


    @Override
    public MspGroup findBySn(String sn) {
        logger.debug("Finding MspGroup by sn: {}", sn);

        return repository.findBySn(sn);
    }

    @Override
    public MspGroup create(MspGroup role) throws GroupException {
        logger.info("Creating role {}", role);
        if(role == null){
            throw new GroupException("MspGroup entry can not be empty.");
        }
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
        repository.create(role);

        return role;
    }

    @Override
    public MspGroup update(MspGroup role) throws GroupException {
        logger.info("Updating role {}", role);
        if(role==null){
            throw new GroupException("MspGroup entry can not be empty.");
        }
        repository.update(role);

        return role;
    }

    @Override
    public MspGroup destroy(MspGroup role) throws GroupException {
        logger.warn("Deleting role {}", role);
        if(role==null){
            throw new GroupException("MspGroup entry can not be empty.");
        }
        repository.delete(role.getSn());
        return role;
    }

    @Override
    public List<MspGroup> search(String keyword) {
        return repository.searchAllByKeyword(keyword);
    }

    @Override
    public MspGroup find(String name) {
        return repository.findByName(name);
    }
}
