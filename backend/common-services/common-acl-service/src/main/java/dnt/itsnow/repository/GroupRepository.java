/**
 * xiongjie on 14-7-23.
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.Group;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <h1>Group Repository</h1>
 *
 * 演示动态schema
 */
public interface GroupRepository {

    @Select("SELECT * FROM groups WHERE name = #{name}")
    Group findByName(@Param("name") String name);

    @Select("SELECT * FROM groups WHERE name LIKE '%#{keyword}%'")
    List<Group> findAllByKeyword(@Param("keyword") String keyword);
}
