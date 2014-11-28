package dnt.itsnow.service;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

/**
 * <h1>组管理业务接口类</h1>
 */
public interface GroupService {

    public Group create(Group group) throws GroupException;

    public Group destroy(Group group) throws GroupException;

    public Group update(Group group) throws GroupException;

    public Page<Group> findAll(String keyword, Pageable pageable);

    public Group findByName(String name);

    List<Group> findAllByUserName(String username);
}
