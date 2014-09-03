/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.exception;

import dnt.itsnow.platform.service.ServiceException;

/**
 * The user service throws exception
 */
public class UserException extends ServiceException {
    public UserException(String message) {
        super(message);
    }
}
