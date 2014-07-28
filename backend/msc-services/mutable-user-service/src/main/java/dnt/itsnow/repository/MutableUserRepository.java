/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Mutable User Repository
 */
public interface MutableUserRepository {

    @Insert("INSERT INTO users(username, email, phone, password, enabled, expired) " +
            "VALUES(#{username}, #{email}, #{phone}, #{password}, TRUE, FALSE)")
    void create(User user);

    /**
     * 只更新用户的用户名，email，电话, enabled, expired
     * @param user 被更新的用户信息
     */
    @Update("UPDATE users SET " +
            " username = #{username}, " +
            " email    = #{email}, " +
            " phone    = #{phone}," +
            " enabled  = #{enabled}," +
            " expired  = #{expired} " +
            " WHERE id = #{id} ")
    void update(User user);

    /**
     * 更新用户的密码
     * @param user 被更新的用户
     * @param newPassword 密码
     */
    @Update("UPDATE users SET " +
            " password = #{password} " +
            " WHERE id = #{user.id} ")
    void changePassword(@Param("user") User user,@Param("password") String newPassword);
}
