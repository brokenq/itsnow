package dnt.itsnow.repository;

import dnt.itsnow.model.Group;
import dnt.itsnow.model.GroupAuthority;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * <h1>MspGroup Repository</h1>
 * <p/>
 * 演示动态schema
 * <p/>
 * TODO 编写测试用例
 */
public interface GeneralGroupRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO groups (group_name, description, created_at, updated_at) VALUES " +
            "(#{name}, #{description}, #{createdAt}, #{updatedAt})")
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

    @Select("select count(0) from groups")
    public int count();

    public List<Group> findAll(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from groups where group_name like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    public List<Group> findAllByKeyword(@Param("keyword") String keyword,
                                           @Param("sort") String sort,
                                           @Param("dir") String dir,
                                           @Param("offset") int offset,
                                           @Param("size") int size);

    @Select("SELECT * FROM groups WHERE UPPER(group_name) LIKE UPPER('%#{keyword}%')")
    List<Group> searchAllByKeyword(@Param("keyword") String keyword);

    public Group findByName(@Param("name") String name);

    @Select(" SELECT ga.authority, g.group_name" +
            " FROM group_authorities ga " +
            " INNER JOIN groups g ON ga.group_id = g.id " +
            " INNER JOIN group_members gm ON gm.group_id = g.id" +
            " WHERE UPPER(gm.username) = UPPER(#{username})" +
            " GROUP BY ga.authority, g.group_name")
    Set<GroupAuthority> findUserAuthorities(@Param("username") String username);

    public int countByRelevantInfo(@Param("keyword") String keyword);

    public List<Group> findAllRelevantInfo(@Param("keyword") String keyword,
                                              @Param("sort") String sort,
                                              @Param("dir") String dir,
                                              @Param("offset") int offset,
                                              @Param("size") int size);

}
