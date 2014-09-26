package dnt.itsnow.service;

import dnt.itsnow.exception.MscRoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>MSC角色Service</h1>
 */
public interface MscRoleService {

    /**
     * <h2>查询所有角色，可分页，可按关键字查询</h2>
     * @param keyword 关键字
     * @param pageable 分页类
     * @return
     */
    public Page<Role> findAll(String keyword, Pageable pageable);

    public Page<Role> findAllRelevantInfo(String keyword, Pageable pageable);

    /**
     * <h2>根据SN查找角色</h2>
     * @param name 角色名称
     * @return
     */
    public Role findByName(String name);

    /**
     * <h2>创建角色</h2>
     * @param role 角色实体类
     * @return
     * @throws MscRoleException
     */
    public Role create(Role role) throws MscRoleException;

    /**
     * <h2>更新角色</h2>
     * @param role 角色实体类
     * @return
     * @throws MscRoleException
     */
    public Role update(Role role) throws MscRoleException;

    /**
     * <h2>删除角色</h2>
     * @param role 角色实体类
     * @return
     * @throws MscRoleException
     */
    public Role destroy(Role role) throws MscRoleException;

}
