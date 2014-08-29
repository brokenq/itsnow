package dnt.itsnow.repository;

import dnt.itsnow.model.MenuItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>菜单项持久层</h1>
 */
public interface MenuItemRepository {

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("INSERT INTO menu_items (parent_id, name, type, state, template_url, url, css, position, shortcut, description) VALUES " +
            "(#{parentId}, #{name}, #{type}, #{state}, #{templateUrl}, #{css}, #{position} ,#{shortcut}, #{description})")
    @SelectKey(statement="call identity()", keyProperty="id", before=false, resultType=long.class)
    public long create(MenuItem menuItem);

    @Delete("DELETE FROM menu_items WHERE id = #{id}")
    public MenuItem delete(@Param("id") Long id);

    @Update("UPDATE menu_items SET " +
            " parent_id    = #{parentId}, " +
            " name         = #{name}, " +
            " type         = #{type}," +
            " state        = #{state}," +
            " template_url = #{templateUrl}," +
            " css          = #{css} " +
            " position     = #{position} " +
            " shortcut     = #{shortcut} " +
            " description  = #{description} " +
            " WHERE id     = #{id} ")
    @SelectKey(statement="call identity()", keyProperty="id", before=false, resultType=long.class)
    public long update(MenuItem menuItem);

    @Select("SELECT * FROM menu_items WHERE id = #{id}")
    public MenuItem findById(@Param("id") Long id);

    @Select("SELECT * FROM menu_items")
    public List<MenuItem> findAll();

}