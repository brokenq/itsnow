/**
 * xiongjie on 14-7-23.
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.Group;
import dnt.itsnow.model.GroupAuthority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * <h1>Authority Repository</h1>
 *
 * 演示动态schema
 *
 * TODO 编写测试用例
 */
public interface AuthorityRepository {

    @Select("SELECT * FROM Authorities WHERE UPPER(group_name) = UPPER(#{name})")
    Group findByName(@Param("name") String name);

    @Select("SELECT * FROM groups WHERE UPPER(group_name) LIKE UPPER('%#{keyword}%')")
    List<Group> findAllByKeyword(@Param("keyword") String keyword);

    @Select(" SELECT ga.authority, g.group_name" +
            " FROM group_authorities ga " +
            " INNER JOIN groups g ON ga.group_id = g.id " +
            " INNER JOIN group_members gm ON gm.group_id = g.id" +
            " WHERE UPPER(gm.username) = UPPER(#{username})" +
            " GROUP BY ga.authority, g.group_name"
    )
    Set<GroupAuthority> findUserAuthorities(@Param("username") String username);
}
