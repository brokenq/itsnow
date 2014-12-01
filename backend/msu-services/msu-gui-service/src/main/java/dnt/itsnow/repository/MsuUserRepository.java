/**
 * xiongjie on 14-7-24.
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import dnt.itsnow.model.UserAuthority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * <h1>查询用户的仓库接口</h1>
 *
 */
public interface MsuUserRepository extends CommonUserRepository {

    User findByMsuAndMspAccountSnAndUsername(@Param("accountSn") String accountSn, @Param("username") String username);

}
