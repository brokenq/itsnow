package dnt.itsnow.service;

import dnt.itsnow.exception.RoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>流程字典Service</h1>
 */
public interface RoleService {

    public Page<Role> findAll(String keyword, Pageable pageable);

    public Role findBySn(String sn);

    public Role create(Role role) throws RoleException;

    public Role update(Role role) throws RoleException;

    public Role destroy(Role role) throws RoleException;

}
