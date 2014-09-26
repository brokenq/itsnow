package dnt.itsnow.repository;

import dnt.itsnow.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public interface RoleRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO roles ( name, description, created_at, updated_at) VALUES " +
            "( #{name}, #{description}, #{createdAt}, #{updatedAt})")
    public void create(Role dictionary);

    @Delete("DELETE FROM roles WHERE name = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE roles SET " +
            " name        = #{name}, " +
            " description = #{description}, " +
            " created_at  = #{createdAt}, " +
            " updated_at  = #{updatedAt} " +
            " WHERE id    = #{id} ")
    public void update(Role dictionary);

    public int count(@Param("accountId") long accountId);

    public List<Role> findAll(
            @Param("accountId") long accountId,
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    public int countByKeyword(
            @Param("accountId") long accountId,
            @Param("keyword") String keyword);

    public List<Role> findAllByKeyword(
            @Param("accountId") long accountId,
            @Param("keyword") String keyword,
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    public int countByRelevantInfo(@Param("keyword") String keyword);

    public List<Role> findAllRelevantInfo(@Param("keyword") String keyword,
                                             @Param("sort") String sort,
                                             @Param("dir") String dir,
                                             @Param("offset") int offset,
                                             @Param("size") int size);

    @Select("SELECT * FROM roles WHERE name = #{name}")
    public Role findByName(@Param("name") String name);

}
