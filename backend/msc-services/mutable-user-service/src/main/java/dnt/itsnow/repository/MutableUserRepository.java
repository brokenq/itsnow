/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <h1>Mutable User Repository</h1>
 * 为了使MutableUserManager的父类UserManager能找到userRepository，
 * 所以本repository应该继承UserRepository
 */
public interface MutableUserRepository extends UserRepository {

    @Select("SELECT count(0) FROM users")
    int count();

    // 采用 Mybatis XML Mapper
    List<User> findUsers(@Param("keyword") String keyword,
                         @Param("pageable") Pageable pageable);

    /**
     * Return the created user id
     *
     * @param user 需要创建的对象
     * @return 创建之后的id
     */
    @Insert("INSERT INTO users(username, email, phone, password, enabled, expired) " +
            "VALUES(#{username}, #{email}, #{phone}, #{password}, TRUE, FALSE)")
    long create(User user);

    /**
     * 只更新用户的用户名，email，电话, enabled, expired
     *
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
     *
     * @param username    被更新的用户
     * @param newPassword 密码
     */
    @Update("UPDATE users SET " +
            " password = #{password} " +
            " WHERE username = #{username} ")
    void changePassword(@Param("username") String username,
                        @Param("password") String newPassword);
}
