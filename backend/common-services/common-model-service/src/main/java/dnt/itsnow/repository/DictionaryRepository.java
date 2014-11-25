package dnt.itsnow.repository;

import dnt.itsnow.model.Dictionary;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>数据字典持久层</h1>
 */
public interface DictionaryRepository {

    public List<Dictionary> findAll(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

    public int count(@Param("keyword") String keyword);

    public Dictionary findByCode(@Param("code") String code);

    public Dictionary findByName(@Param("name") String name);

    public Dictionary findByLabel(@Param("label") String label);

    public void create(Dictionary dictionary);

    public void update(Dictionary dictionary);

    @Delete("DELETE FROM dictionaries WHERE code = #{code}")
    public void delete(@Param("code") String code);
}
