/**
 * Developer: Kadvin Date: 14-7-14 下午3:22
 */
package dnt.itsnow.services.api;

import dnt.itsnow.services.model.User;

/**
 * the Sample User service
 */
public interface UserService {
    User find(String username, String password);
}
