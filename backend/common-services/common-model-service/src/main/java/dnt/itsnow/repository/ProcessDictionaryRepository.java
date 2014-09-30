package dnt.itsnow.repository;

import dnt.itsnow.model.ProcessDictionary;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>数据字典持久层</h1>
 */
public interface ProcessDictionaryRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO process_dictionaries " +
            "(sn,    code,    name,    display,    val,    state,    type,    description,    created_at,   updated_at) " +
            "VALUES " +
            "(#{sn}, #{code}, #{name}, #{display}, #{val}, #{state}, #{type}, #{description} ,#{createdAt}, #{updatedAt})")
    public void create(ProcessDictionary dictionary);

    @Delete("DELETE FROM process_dictionaries WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE process_dictionaries SET " +
            " sn          = #{sn}, " +
            " code        = #{code}, " +
            " name        = #{name}, " +
            " display         = #{display}," +
            " val       = #{val}," +
            " state       = #{state}," +
            " type        = #{type}, " +
            " description = #{description}, " +
            " created_at  = #{createdAt}, " +
            " updated_at  = #{updatedAt} " +
            " WHERE id    = #{id} ")
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
    public List<ProcessDictionary> findByCode(@Param("code") String code);

    @Select("SELECT * FROM process_dictionaries WHERE sn = #{sn}")
    public ProcessDictionary findBySn(@Param("sn") String sn);
}
