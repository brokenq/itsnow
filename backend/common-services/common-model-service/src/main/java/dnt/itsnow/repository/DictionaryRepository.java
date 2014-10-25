package dnt.itsnow.repository;

import dnt.itsnow.model.Dictionary;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>数据字典持久层</h1>
 */
public interface DictionaryRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO dictionaries " +
            "(sn,    code,    name,    display,    val,    state,    type,    description,    created_at,   updated_at) " +
            "VALUES " +
            "(#{sn}, #{code}, #{name}, #{display}, #{val}, #{state}, #{type}, #{description} ,#{createdAt}, #{updatedAt})")
    public void create(Dictionary dictionary);

    @Delete("DELETE FROM dictionaries WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE dictionaries SET " +
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
    public void update(Dictionary dictionary);

    public int count(@Param("keyword") String keyword);

    public List<Dictionary> findAll(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

    @Select("SELECT * FROM dictionaries WHERE code = #{code}")
    public List<Dictionary> findByCode(@Param("code") String code);

    @Select("SELECT * FROM dictionaries WHERE sn = #{sn}")
    public Dictionary findBySn(@Param("sn") String sn);
}
