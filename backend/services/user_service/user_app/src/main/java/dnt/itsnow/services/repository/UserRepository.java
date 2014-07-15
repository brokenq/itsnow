/**
 * Developer: Kadvin Date: 14-7-14 下午3:23
 */
package dnt.itsnow.services.repository;

import dnt.itsnow.services.model.User;
import org.apache.ibatis.annotations.Select;

/**
 * The mybatis user repository
 */
public interface UserRepository {
    @Select("select * from users where name = {name} and password = PASSWORD({password})")
    User findByNameAndPassword(String name, String password);
}
