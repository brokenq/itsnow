package dnt.itsnow.repository;

import dnt.itsnow.model.Role;
import dnt.itsnow.model.User;
import dnt.itsnow.model.UserAuthority;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>角色管理持久层</h1>
 */
public interface RoleRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO roles " +
            "( name,    description,    created_at,   updated_at) " +
            "VALUES " +
            "( #{name}, #{description}, #{createdAt}, #{updatedAt})")
    public void create(Role role);

    @Delete("DELETE FROM roles WHERE name = #{name}")
    public void delete(@Param("name") String name);

    @Update("UPDATE roles SET " +
            " name        = #{name}, " +
            " description = #{description}, " +
            " created_at  = #{createdAt}, " +
            " updated_at  = #{updatedAt} " +
            " WHERE id    = #{id} ")
    public void update(Role role);

    public int count(@Param("keyword") String keyword);

    /**
     * 查询所有角色及其相关的用户信息
     * @param keyword 关键字
     * @param pageable 分页类
     * @return 角色及其相关的用户信息
     */
    public List<Role> findAll(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

    public Role findByName(@Param("name") String name);

    /**
     * 创建角色和用户关系
     *
     * @param userAuthority 用户与角色关系实体类
     */
    @Insert("INSERT INTO authorities ( username, authority) VALUES (#{username}, #{authority})")
    public void createRoleAndUserRelation(UserAuthority userAuthority);

    /**
     * 删除角色和用户关系
     *
     * @param userAuthority 用户与角色关系实体类
     */
    @Delete("DELETE FROM authorities WHERE username = #{username} and authority = #{authority}")
    public void deleteRoleAndUserRelation(UserAuthority userAuthority);

    /**
     * 纯属为这测试才写了此方法
     *
     * @param userAuthority 用户与角色关系实体类
     * @return
     */
    @Select("SELECT * FROM authorities WHERE username = #{username} and authority = #{authority}")
    public UserAuthority findRoleAndUserRelation(UserAuthority userAuthority);

    /**
     * 删除所包含有此角色名的角色与用户关系
     *
     * @param authority 角色名
     */
    @Delete("DELETE FROM authorities WHERE authority = #{authority}")
    public void deleteRoleAndUserRelationByRoleName(@Param("authority") String authority);

    /**
     * 删除所包含有此角色名的角色与组关系
     *
     * @param authority 角色名
     */
    @Delete("DELETE FROM group_authorities WHERE authority = #{authority}")
    public void deleteRoleAndGroupRelationByRoleName(@Param("authority") String authority);

    /**
     * 根据账户序列号查询所属用户列表
     * @param id 账户ID
     * @return 用户列表
     */
    @Select("SELECT * FROM itsnow_msc.users WHERE account_id = #{id}")
    List<User> findUsersByAccountId(@Param("id") Long id);
}
