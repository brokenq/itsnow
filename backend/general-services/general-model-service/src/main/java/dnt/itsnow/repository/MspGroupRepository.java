package dnt.itsnow.repository;

import dnt.itsnow.model.GroupAuthority;
import dnt.itsnow.model.MspGroup;
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
public interface MspGroupRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO groups (sn, group_name, description, created_at, updated_at) VALUES " +
            "(#{sn}, #{name}, #{description}, #{createdAt}, #{updatedAt})")
    public void create(MspGroup group);

    @Delete("DELETE FROM groups WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE groups SET " +
            " sn                    = #{sn}, " +
            " group_name                  = #{name}, " +
            " description           = #{description}," +
            " created_at            = #{createdAt}, " +
            " updated_at            = #{updatedAt} " +
            " WHERE id              = #{id} ")
    public void update(MspGroup group);

    @Select("select count(0) from groups")
    public int count();

    public List<MspGroup> findAll(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from groups where group_name like #{keyword} or sn like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    public List<MspGroup> findAllByKeyword(@Param("keyword") String keyword,
                                           @Param("sort") String sort,
                                           @Param("dir") String dir,
                                           @Param("offset") int offset,
                                           @Param("size") int size);

    @Select("SELECT * FROM groups WHERE UPPER(group_name) LIKE UPPER('%#{keyword}%')")
    List<MspGroup> searchAllByKeyword(@Param("keyword") String keyword);

    public MspGroup findBySn(@Param("sn") String sn);

    @Select("SELECT * FROM groups WHERE UPPER(group_name) = UPPER(#{name})")
    MspGroup findByName(@Param("name") String name);

    @Select(" SELECT ga.authority, g.group_name" +
            " FROM group_authorities ga " +
            " INNER JOIN groups g ON ga.group_id = g.id " +
            " INNER JOIN group_members gm ON gm.group_id = g.id" +
            " WHERE UPPER(gm.username) = UPPER(#{username})" +
            " GROUP BY ga.authority, g.group_name")
    Set<GroupAuthority> findUserAuthorities(@Param("username") String username);

    public List<MspGroup> findAllRelevantInfo(@Param("keyword") String keyword,
                                              @Param("sort") String sort,
                                              @Param("dir") String dir,
                                              @Param("offset") int offset,
                                              @Param("size") int size);

}
