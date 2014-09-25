package dnt.itsnow.repository;

import dnt.itsnow.model.MscRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public interface MscRoleRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO roles ( name, description, created_at, updated_at) VALUES " +
            "( #{name}, #{description}, #{createdAt}, #{updatedAt})")
    public void create(MscRole dictionary);

    @Delete("DELETE FROM roles WHERE name = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE roles SET " +
            " name        = #{name}, " +
            " description = #{description}, " +
            " created_at  = #{createdAt}, " +
            " updated_at  = #{updatedAt} " +
            " WHERE id    = #{id} ")
    public void update(MscRole dictionary);

    @Select("select count(0) from roles")
    public int count();

    @Select("select * from roles order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<MscRole> findAll(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from roles where name like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    @Select("select * from roles where name like #{keyword}" +
            " order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<MscRole> findAllByKeyword(@Param("keyword") String keyword,
                                       @Param("sort") String sort,
                                       @Param("dir") String dir,
                                       @Param("offset") int offset,
                                       @Param("size") int size);

    public int countByRelevantInfo(@Param("keyword") String keyword);

    public List<MscRole> findAllRelevantInfo(@Param("keyword") String keyword,
                                                  @Param("sort") String sort,
                                                  @Param("dir") String dir,
                                                  @Param("offset") int offset,
                                                  @Param("size") int size);

    @Select("SELECT * FROM roles WHERE name = #{name}")
    public MscRole findByName(@Param("name") String name);

}
