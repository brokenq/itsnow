/**
 * Developer: Kadvin Date: 14-7-14 下午3:23
 */
package dnt.itsnow.demo.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * the sample user exception
 */
public class UserException extends ServiceException{
    public UserException(String message) {
        super(message);
    }
}
