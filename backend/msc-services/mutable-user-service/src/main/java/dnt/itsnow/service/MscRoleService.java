package dnt.itsnow.service;

import dnt.itsnow.exception.MscRoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.model.UserAuthority;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;

/**
 * <h1>MSC角色管理业务层</h1>
 */
public interface MscRoleService {

    /**
     * <h2>查询所有角色，可分页，可按关键字查询</h2>
     * <p></p>
     * @param keyword 关键字
     * @param pageRequest 分页实体类
     * @return 角色列表
     */
    public Page<Role> findAll(String keyword, PageRequest pageRequest);

    /**
     * <h2>查询指定角色的相关信息</h2>
     * <p></p>
     * @param name 要查询的角色名称
     * @param pageRequest 分页实体类
     * @return 角色列表
     */
    public Page<Role> findAllRelevantInfo(String name, PageRequest pageRequest);

    /**
     * <h2>根据名称查找角色</h2>
     * <p></p>
     * @param name 角色名称
     * @return 角色列表
     */
    public Role findByName(String name);

    /**
     * <h2>创建角色</h2>
     * <p></p>
     * @param role 待创建的角色
     * @return 已创建的角色
     * @throws dnt.itsnow.exception.MscRoleException
     */
    public Role create(Role role) throws MscRoleException;

    /**
     * <h2>更新角色</h2>
     * <p></p>
     * @param role 角色实体类
     * @return 已更新的角色
     * @throws dnt.itsnow.exception.MscRoleException
     */
    public Role update(Role role) throws MscRoleException;

    /**
     * <h2>删除角色</h2>
     * <p></p>
     * @param role 角色实体类
     * @throws dnt.itsnow.exception.MscRoleException
     */
    public void destroy(Role role) throws MscRoleException;

    /**
     * 创建角色和用户关系表
     * @param userAuthority 用户与角色关系实体类
     */
    public UserAuthority createRoleAndUserRelation(UserAuthority userAuthority) throws MscRoleException;

    /**
     * 删除角色和用户关系
     * @param userAuthority 用户与角色关系实体类
     */
    public void destroyRoleAndUserRelation(UserAuthority userAuthority) throws MscRoleException;

}
