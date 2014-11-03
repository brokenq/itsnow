/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>Mutable User Repository</h1>
 * 为了使MutableUserManager的父类UserManager能找到userRepository，
 * 所以本repository应该继承UserRepository
 */
public interface MutableUserRepository extends CommonUserRepository {

    // 采用 Mybatis XML Mapper
    long countByKeyword(@Param("keyword") String keyword);

    // 采用 Mybatis XML Mapper
    List<User> findAllByKeyword(@Param("keyword") String keyword,
                                @Param("pageable") Pageable pageable);

    /**
     * Return the created user id
     *
     * @param user 需要创建的对象
     */
    @Insert("INSERT INTO itsnow_msc.users(account_id, username,nick_name, email, phone, password, enabled, expired, created_at, updated_at) " +
            "VALUES(#{accountId}, #{username}, #{nickName},#{email}, #{phone}, #{password}, TRUE, FALSE, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    void create(User user);

    /**
     * 只更新用户的email，电话, enabled, expired
     *
     * @param user 被更新的用户信息
     */
    @Update("UPDATE itsnow_msc.users SET " +
            " email    = #{email}, " +
            " nick_name =#{nickName}," +
            " phone    = #{phone}," +
            " enabled  = #{enabled}," +
            " expired  = #{expired}, " +
            " updated_at = #{updatedAt}" +
            " WHERE username = #{username} ")
    void update(User user);

    /**
     * 更新用户的密码
     *
     * @param username    被更新的用户
     * @param newPassword 密码
     */
    @Update("UPDATE itsnow_msc.users SET " +
            " password = #{password}, " +
            " updated_at = #{updatedAt}" +
            " WHERE username = #{username} ")
    void changePassword(@Param("username") String username,
                        @Param("password") String newPassword);

    @Delete("DELETE FROM itsnow_msc.users WHERE account_id = #{accountId}")
    void deleteAllByAccountId(@Param("accountId") Long accountId);
    @Select("SELECT users.expired," +
            "users.username," +
            "users.nick_name," +
            "users.email," +
            "users.enabled," +
            "users.phone FROM itsnow_msc.users WHERE username=#{username}")
    User findByUsername(@Param("username") String username);
    @Select("SELECT * FROM itsnow_msc.users WHERE email=#{email}")
    User findByEmail(@Param("email") String email);
    @Delete("DELETE FROM itsnow_msc.users WHERE username = #{username}")
    void delete(User user);
}
