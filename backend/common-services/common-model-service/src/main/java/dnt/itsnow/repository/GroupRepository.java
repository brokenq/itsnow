package dnt.itsnow.repository;

import dnt.itsnow.model.Group;
import dnt.itsnow.model.GroupAuthority;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * <h1>Group Repository</h1>
 * <p/>
 */
public interface GroupRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO groups (sn,group_name, description, created_at, updated_at) VALUES " +
            "(#{sn},#{name}, #{description}, #{createdAt}, #{updatedAt})")
    public void create(Group group);

    @Delete("DELETE FROM groups WHERE group_name = #{name}")
    public void delete(@Param("name") String name);

    @Delete("DELETE FROM group_authorities WHERE group_id  = #{id}")
    public void deleteGroupAuthority(@Param("id") Long id);

    @Delete("DELETE FROM group_members WHERE group_id  = #{id}")
    public void deleteGroupMember(@Param("id") Long id);

    @Update("UPDATE groups SET " +
            " group_name            = #{name}, " +
            " description           = #{description}," +
            " created_at            = #{createdAt}, " +
            " updated_at            = #{updatedAt} " +
            " WHERE id              = #{id} ")
    public void update(Group group);

    public int count(@Param("keyword") String keyword);

    public List<Group> findAll(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

    public Group findByName(@Param("name") String name);

    @Select(" SELECT ga." +
            "authority, g.group_name" +
            " FROM group_authorities ga " +
            " INNER JOIN groups g ON ga.group_id = g.id " +
            " INNER JOIN group_members gm ON gm.group_id = g.id" +
            " WHERE UPPER(gm.username) = UPPER(#{username})" +
            " GROUP BY ga.authority, g.group_name")
    Set<GroupAuthority> findUserAuthorities(@Param("username") String username);

}
