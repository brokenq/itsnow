/**
 * Developer: Kadvin Date: 14/11/4 下午8:15
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.Sequence;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * The DB Sequence service
 */
public interface SequenceRepository {
    @Select("SELECT NEXT_VAL(#{catalog})")
    long nextValue(@Param("catalog") String catalog);

    @Select("SELECT CURR_VAL(#{catalog})")
    long currValue(@Param("catalog") String catalog);

    @Select("SELECT SET_VAL(#{catalog}, #{value})")
    long setValue(@Param("catalog") String catalog, @Param("value") int value);

    @Select("SELECT * FROM sequences")
    @ResultMap("sequenceResult")
    List<Sequence> findAll();

    @Select("SELECT * FROM sequences WHERE catalog = #{catalog}")
    @ResultMap("sequenceResult")
    Sequence findByCatalog(@Param("catalog") String catalog);

    @Update("UPDATE sequences SET rule = #{rule}, increment = #{increment} WHERE catalog = #{catalog}")
    void update(Sequence sequence);

    @Insert("INSERT INTO sequences(catalog, rule, value, increment) VALUES(#{catalog}, #{rule}, #{value}, #{increment})")
    void create(Sequence sequence);

    @Delete("DELETE FROM sequences WHERE catalog = #{catalog}")
    void deleteByCatalog(@Param("catalog") String catalog);
}
