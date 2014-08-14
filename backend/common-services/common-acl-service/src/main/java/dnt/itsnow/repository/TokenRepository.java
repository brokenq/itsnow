/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.util.Date;

/**
 * Mybatis persistent logins repository
 */
public interface TokenRepository {
    @Insert("INSERT INTO persistent_logins (username, series, token, last_used)" +
            " values(#{username},#{series},#{token},#{last_used})")
    void save(PersistentRememberMeToken token);

    @Update("UPDATE persistent_logins SET token = #{token}, last_used = #{lastUsed} where series = #{series}")
    void update(@Param("series") String series, @Param("token") String token, @Param("lastUsed") Date lastUsed);

    @Select("SELECT username,series,token as tokenValue,last_used FROM persistent_logins WHERE series = #{series}")
    PersistentRememberMeToken findBySeries(@Param("series") String series);

    @Delete("DELETE FROM persistent_logins WHERE username = #{username}")
    void delete(@Param("username") String username);
}
