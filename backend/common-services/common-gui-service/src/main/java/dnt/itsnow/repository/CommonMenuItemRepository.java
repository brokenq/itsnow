package dnt.itsnow.repository;

import dnt.itsnow.model.MenuItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Sin on 2014/8/22.
 */
public interface CommonMenuItemRepository {

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("INSERT INTO MENU_ITEMS (parent_id, name, type, url, css, show_order, short_cut, description) VALUES" +
            "(#{parentId}, #{name}, #{type}, #{url}, #{css}, #{show_order} ,#{short_cut}, #{description});")
    void save(MenuItem menuItem);

    @Update("UPDATE MENU_ITEMS SET " +
            " parent_id   = #{parentId}, " +
            " name        = #{name}, " +
            " type        = #{type}," +
            " url         = #{url}," +
            " css         = #{css} " +
            " show_order  = #{show_order} " +
            " short_cut   = #{short_cut} " +
            " description = #{description} " +
            " WHERE id    = #{id} ")
    void update(MenuItem menuItem);

    @Select("SELECT * FROM MENU_ITEMS WHERE id = #{id}")
    public MenuItem findById(@Param("id") Long id);

    @Select("SELECT * FROM MENU_ITEMS WHERE parent_id is null order by show_order")
    public List<MenuItem> findByMainMenu();

    @Select("SELECT * FROM MENU_ITEMS WHERE parent_id = #{parent_id} order by show_order")
    public List<MenuItem> findBySubMenu(@Param("parent_id") Long parentId);

    @Select("SELECT * FROM MENU_ITEMS")
    public List<MenuItem> findAll();

}
