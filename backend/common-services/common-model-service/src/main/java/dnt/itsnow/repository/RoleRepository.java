package dnt.itsnow.repository;

import dnt.itsnow.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public interface RoleRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO roles (sn, name, description, created_at, updated_at) VALUES " +
            "(#{sn}, #{name}, #{description}, #{createdAt}, #{updatedAt})")
    public void create(Role dictionary);

    @Delete("DELETE FROM roles WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE roles SET " +
            " sn          = #{sn}, " +
            " name        = #{name}, " +
            " description = #{description}, " +
            " created_at  = #{createdAt}, " +
            " updated_at  = #{updatedAt} " +
            " WHERE id    = #{id} ")
    public void update(Role dictionary);

    @Select("select count(0) from roles")
    public int count();

    public List<Role> find(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from roles where name like #{keyword} or sn like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    public List<Role> findByKeyword(@Param("keyword") String keyword,
                                                 @Param("sort") String sort,
                                                 @Param("dir") String dir,
                                                 @Param("offset") int offset,
                                                 @Param("size") int size);

    public Role findBySn(@Param("sn") String sn);
}
