package dnt.itsnow.repository;

import dnt.itsnow.model.ProcessDictionary;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public interface ProcessDictionaryRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO process_dictionaries (code, name, level, level_name, state, type, description, created_at, updated_at) VALUES " +
            "(#{code}, #{name}, #{level}, #{levelName}, #{state}, #{type}, #{description} ,#{createdAt}, #{updatedAt})")
    @SelectKey(statement = "call identity()", keyProperty = "id", before = false, resultType = long.class)
    public void create(ProcessDictionary dictionary);

    @Delete("DELETE FROM process_dictionaries WHERE code = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE process_dictionaries SET " +
            " code        = #{code}, " +
            " name        = #{name}, " +
            " level       = #{level}," +
            " level_name  = #{levelName}," +
            " state       = #{state}," +
            " type        = #{type}, " +
            " description = #{description}, " +
            " created_at  = #{createdAt}, " +
            " updated_at  = #{updatedAt} " +
            " WHERE id    = #{id} ")
    @SelectKey(statement = "call identity()", keyProperty = "id", before = false, resultType = long.class)
    public void update(ProcessDictionary dictionary);

    @Select("select count(0) from process_dictionaries")
    public int count();

    @Select("select * from process_dictionaries order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<ProcessDictionary> find(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from process_dictionaries where name like #{keyword} or code like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    @Select("select * from process_dictionaries where name like #{keyword} or code like #{keyword}" +
            " order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<ProcessDictionary> findByKeyword(@Param("keyword") String keyword,
                                                 @Param("sort") String sort,
                                                 @Param("dir") String dir,
                                                 @Param("offset") int offset,
                                                 @Param("size") int size);

    @Select("SELECT * FROM process_dictionaries WHERE code = #{code}")
    public ProcessDictionary findByCode(@Param("code") String code);
}
