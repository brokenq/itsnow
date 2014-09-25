package dnt.itsnow.service;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

/**
 * <h1>Service</h1>
 */
public interface MscGroupService {

    public Page<Group> findAll(String keyword, Pageable pageable);

    public Page<Group> findAllRelevantInfo(String keyword, Pageable pageable);

    public Group findBySn(String sn);

    public Group create(Group group) throws GroupException;

    public Group update(Group group) throws GroupException;

    public Group destroy(Group group) throws GroupException;

    List<Group> search(String keyword);

    Group find(String name);

}
