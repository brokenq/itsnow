package dnt.itsnow.service;

import dnt.itsnow.exception.RoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>MSC角色Service</h1>
 */
public interface RoleService {

    /**
     * <h2>查询所有角色，可分页，可按关键字查询</h2>
     * @param keyword 关键字
     * @param pageable 分页类
     * @return
     */
    public Page<Role> findAll(Long accountId,String keyword, Pageable pageable);

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
     * @throws RoleException
     */
    public Role create(Role role) throws RoleException;

    /**
     * <h2>更新角色</h2>
     * @param role 角色实体类
     * @return
     * @throws RoleException
     */
    public Role update(Role role) throws RoleException;

    /**
     * <h2>删除角色</h2>
     * @param role 角色实体类
     * @return
     * @throws RoleException
     */
    public Role destroy(Role role) throws RoleException;

}
