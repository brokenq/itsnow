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
public interface CommonUserRepository {
    //@Select("SELECT * FROM itsnow_msc.users where UPPER(username) = UPPER(#{username})")
    // USE XML which will find user with account
    User findByFieldAndValue(@Param("field") String field, @Param("value") String value);

    @Select("SELECT * FROM authorities WHERE UPPER(username) = UPPER(#{username})")
    Set<UserAuthority> findAuthorities(@Param("username") String username);

    User findByAccountSnAndUsername(@Param("accountSn") String accountSn, @Param("username")String username);

    @Select("SELECT * FROM itsnow_msc.users WHERE account_id = #{mainAccount.id}")
    List<User> findUsersByAccount(@Param("mainAccount")Account mainAccount);

    /**
     * 根据合同查询相关的MSP用户
     * @param mainAccount MSU账户
     * @return MSP用户列表
     */
    List<User> findAllByContract(Account mainAccount);

    /**
     * 查询指定账户下所有用户和MSU系统中与MSP签订合同的MSP用户
     * @param mainAccount 账户
     * @return 用户列表
     */
    List<User> findAllByAccountAndContract(Account mainAccount);

    /**
     * 查询所有可登录MSU系统的MSP用户
     * @param accountId MSU账户
     * @return MSP用户
     */
    List<User> findAllByLogOnContract(@Param("accountId")Long accountId);
}
