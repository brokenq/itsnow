/**
 * Developer: Kadvin Date: 14-7-14 下午3:23
 */
package dnt.itsnow.demo.repository;

import dnt.itsnow.demo.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <h2>The mybatis user repository</h2>
 *
 * 经过实际使用，发现annotation方式不支持 overload， 多个映射方法不能同名
 */
public interface UserRepository {
    @Select("SELECT * FROM demo_users WHERE name = #{name} AND password = PASSWORD(#{password})")
    User findByNameAndPassword(@Param("name") String name,
                               @Param("password") String password);

    @Select("SELECT * FROM demo_users WHERE name = #{name}")
    User findByName(@Param("name") String username);

    //这个地方，用 Annotation 方式不如 XML方式
    // XML方式可以用 where, if[test] 等方式组合SQL，
    // 让仓库能够很好的支持 只用单个SQL，支持 有keyword或者没有keyword
    // 甚至可以省掉 offset, size

    @Select("SELECT * FROM demo_users " +
            " ORDER BY #{orderBy} LIMIT #{offset}, #{size}")
    List<User> findUsers(@Param("orderBy") String orderBy,
                         @Param("offset") int offset,
                         @Param("size") int size);

    @Select("SELECT * FROM demo_users WHERE " +
            "name LIKE '%#{keyword}%' OR nick_name LIKE '%#{keyword}%'" +
            " ORDER BY #{orderBy} LIMIT #{offset}, #{size}")
    List<User> findUsersByKeyword(@Param("keyword") String keyword,
                                  @Param("orderBy") String orderBy,
                                  @Param("offset") int offset,
                                  @Param("size") int size);

    @Select("SELECT count(0) FROM demo_users")
    int count();//如果是count，mybatis会说已经这么映射了，但我貌似又没法用到

    @Select("SELECT count(0) FROM demo_users WHERE " +
            " name LIKE '%#{keyword}%' OR nick_name LIKE '%#{keyword}%'")
    int countByKeyword(@Param("keyword") String keyword);

}
