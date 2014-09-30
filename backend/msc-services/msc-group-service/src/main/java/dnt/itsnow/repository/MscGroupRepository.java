package dnt.itsnow.repository;

import dnt.itsnow.model.Group;
import dnt.itsnow.model.GroupAuthority;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * <h1>MspGroup Repository</h1>
 */
public interface MscGroupRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO groups " +
            "(group_name, description,    created_at,   updated_at) " +
            "VALUES " +
            "(#{name},    #{description}, #{createdAt}, #{updatedAt})")
    public void create(Group group);

    @Delete("DELETE FROM groups WHERE group_name = #{name}")
    public void delete(@Param("name") String name);

    @Delete("DELETE FROM group_members WHERE group_name = #{name}")
    public void deleteGroupAndUserRelationByGroupName(@Param("name") String name);

    @Update("UPDATE groups SET " +
            " group_name            = #{name}, " +
            " description           = #{description}," +
            " created_at            = #{createdAt}, " +
            " updated_at            = #{updatedAt} " +
            " WHERE id              = #{id} ")
    public void update(Group group);

    @Select("select count(0) from groups")
    public int count();

    @Select("select id, group_name as name, description, created_at, updated_at from groups" +
            " order by ${sort} ${dir}" +
            " limit #{offset}, #{size}")
    public List<Group> findAll(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from groups where group_name like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    @Select("select id, group_name as name, description, created_at, updated_at from groups" +
            " where group_name like #{keyword}" +
            " order by ${sort} ${dir}" +
            " limit #{offset}, #{size}")
    public List<Group> findAllByKeyword(
            @Param("keyword") String keyword,
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("SELECT id, group_name as name, description, created_at, updated_at FROM groups" +
            " WHERE group_name LIKE #{keyword}")
    List<Group> searchAllByKeyword(@Param("keyword") String keyword);

    @Select("SELECT id, group_name as name, description, created_at, updated_at FROM groups" +
            " WHERE group_name = #{name}")
    public Group findByName(@Param("name") String name);

    @Select(" SELECT ga.authority, g.group_name" +
            " FROM group_authorities ga " +
            " INNER JOIN groups g ON ga.group_id = g.id " +
            " INNER JOIN group_members gm ON gm.group_id = g.id" +
            " WHERE gm.username = #{username}" +
            " GROUP BY ga.authority, g.group_name")
    Set<GroupAuthority> findUserAuthorities(@Param("username") String username);

    public int countByRelevantInfo(@Param("name") String name);

    public List<Group> findAllRelevantInfo(
            @Param("name") String name,
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

}
