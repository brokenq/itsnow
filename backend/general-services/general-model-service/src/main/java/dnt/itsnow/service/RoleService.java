package dnt.itsnow.service;

import dnt.itsnow.exception.RoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.PageRequest;

/**
 * <h1>MSP/MSU角色Service</h1>
 */
public interface RoleService {

    /**
     * <h2>查询所有角色，可分页，可按关键字查询</h2>
     * <p/>
     * @param accountId 账户ID
     * @param keyword 关键字
     * @param pageRequest 分页类
     * @return 角色列表
     */
    public Page<Role> findAll(Long accountId, String keyword, PageRequest pageRequest);

    /**
     * <h2>查询指定角色相关联的信息</h2>
     * <p/>
     * @param name 角色名称
     * @param pageRequest 分页类
     * @return 角色列表
     */
    public Page<Role> findAllRelevantInfo(String name, PageRequest pageRequest);

    /**
     * <h2>根据角色名称进行查找</h2>
     * <p/>
     * @param name 角色名称
     * @return 角色实体类
     */
    public Role findByName(String name);

    /**
     * <h2>创建角色</h2>
     * @param role 角色实体类
     * @return 角色实体类
     * @throws RoleException
     */
    public Role create(Role role) throws RoleException;

    /**
     * <h2>更新角色</h2>
     * @param role 角色实体类
     * @return 角色实体类
     * @throws RoleException
     */
    public Role update(Role role) throws RoleException;

    /**
     * <h2>删除角色</h2>
     * @param role 角色实体类
     * @throws RoleException
     */
    public void destroy(Role role) throws RoleException;

}
