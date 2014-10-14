package dnt.itsnow.repository;

import dnt.itsnow.model.Role;
import dnt.itsnow.model.UserAuthority;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>MSC角色管理持久层</h1>
 */
public interface MscRoleRepository {

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

    @Select("select count(0) from roles")
    public int count();

    @Select("select * from roles order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<Role> findAll(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from roles where name like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    @Select("select * from roles where name like #{keyword}" +
            " order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<Role> findAllByKeyword(
            @Param("keyword") String keyword,
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    public int countByRelevantInfo(@Param("name") String name);

    public List<Role> findAllRelevantInfo(
            @Param("name") String name,
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("SELECT * FROM roles WHERE name = #{name}")
    public Role findByName(@Param("name") String name);

    /**
     * 创建角色和用户关系
     * @param userAuthority 用户与角色关系实体类
     */
    @Insert("INSERT INTO authorities ( username, authority) VALUES (#{username}, #{authority})")
    public void createRoleAndUserRelation(UserAuthority userAuthority);

    /**
     * 删除角色和用户关系
     * @param userAuthority 用户与角色关系实体类
     */
    @Delete("DELETE FROM authorities WHERE username = #{username} and authority = #{authority}")
    public void deleteRoleAndUserRelation(UserAuthority userAuthority);

    /**
     * 纯属为这测试才写了此方法
     * @param userAuthority 用户与角色关系实体类
     * @return
     */
    @Select("SELECT * FROM authorities WHERE username = #{username} and authority = #{authority}")
    public UserAuthority findRoleAndUserRelation(UserAuthority userAuthority);

    /**
     * 删除所包含有此角色名的角色与用户关系
     * @param authority 角色名
     */
    @Delete("DELETE FROM authorities WHERE authority = #{authority}")
    public void deleteRoleAndUserRelationByRoleName(@Param("authority") String authority);

    /**
     * 删除所包含有此角色名的角色与组关系
     * @param authority 角色名
     */
    @Delete("DELETE FROM group_authorities WHERE authority = #{authority}")
    public void deleteRoleAndGroupRelationByRoleName(@Param("authority") String authority);

}
