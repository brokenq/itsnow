package dnt.itsnow.repository;

import dnt.itsnow.model.Role;
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

    public Long count(@Param("accountId") long accountId);

    public List<Role> findAll(
            @Param("accountId") long accountId,
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    public Long countByKeyword(
            @Param("accountId") long accountId,
            @Param("keyword") String keyword);

    public List<Role> findAllByKeyword(
            @Param("accountId") long accountId,
            @Param("keyword") String keyword,
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    public Long countByRelevantInfo(@Param("name") String name);

    public List<Role> findAllRelevantInfo(@Param("name") String name,
                                             @Param("sort") String sort,
                                             @Param("dir") String dir,
                                             @Param("offset") int offset,
                                             @Param("size") int size);

    @Select("SELECT * FROM roles WHERE name = #{name}")
    public Role findByName(@Param("name") String name);

}
