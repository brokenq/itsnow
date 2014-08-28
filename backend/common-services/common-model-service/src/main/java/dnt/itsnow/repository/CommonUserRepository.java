/**
 * xiongjie on 14-7-24.
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.User;
import dnt.itsnow.model.UserAuthority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <h1>查询用户的仓库接口</h1>
 *
 * TODO 编写测试用例
 */
public interface CommonUserRepository {
    //@Select("SELECT * FROM itsnow_msc.users where UPPER(username) = UPPER(#{username})")
    // USE XML which will find user with account
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM authorities WHERE UPPER(username) = UPPER(#{username})")
    Set<UserAuthority> findAuthorities(@Param("username") String username);
}
