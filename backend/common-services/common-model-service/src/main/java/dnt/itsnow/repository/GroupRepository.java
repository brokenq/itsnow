package dnt.itsnow.repository;

import dnt.itsnow.model.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>Group Repository</h1>
 * <p/>
 * 演示动态schema
 * <p/>
 * TODO 编写测试用例
 */
public interface GroupRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO groups (sn, name, description, created_at, updated_at) VALUES " +
            "(#{sn}, #{name}, #{description}, #{createdAt}, #{updatedAt})")
    public void create(Group group);

    @Delete("DELETE FROM groups WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE groups SET " +
            " sn                    = #{sn}, " +
            " name                  = #{name}, " +
            " description           = #{description}," +
            " created_at            = #{createdAt}, " +
            " updated_at            = #{updatedAt} " +
            " WHERE id              = #{id} ")
    public void update(Group group);

    @Select("select count(0) from groups")
    public int count();

    public List<Group> find(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from groups where name like #{keyword} or sn like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    public List<Group> findByKeyword(@Param("keyword") String keyword,
                                     @Param("sort") String sort,
                                     @Param("dir") String dir,
                                     @Param("offset") int offset,
                                     @Param("size") int size);

    public Group findBySn(@Param("sn") String sn);
}
