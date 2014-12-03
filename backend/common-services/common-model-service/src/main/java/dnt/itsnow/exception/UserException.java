/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * The user service throws exception
 */
public class UserException extends ServiceException {
    public UserException(String message) {
        super(message);
    }
}
