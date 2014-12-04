/**
 * Developer: Kadvin Date: 14-7-14 下午3:22
 */
package dnt.itsnow.demo.api;

import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.service.Pageable;
import dnt.itsnow.demo.model.User;

/**
 * the Sample User service
 */
public interface UserService {
    User find(String username, String password);

    User find(String username);

    Page<User> findAll(String keyword, Pageable pageable);
}
