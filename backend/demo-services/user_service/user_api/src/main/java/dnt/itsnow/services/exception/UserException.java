/**
 * Developer: Kadvin Date: 14-7-14 下午3:23
 */
package dnt.itsnow.services.exception;

import dnt.itsnow.platform.service.ServiceException;

/**
 * the sample user exception
 */
public class UserException extends ServiceException{
    public UserException(String message) {
        super(message);
    }
}
