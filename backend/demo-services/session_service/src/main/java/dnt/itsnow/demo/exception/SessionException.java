/**
 * Developer: Kadvin Date: 14-7-10 下午9:54
 */
package dnt.itsnow.demo.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * Session Service thrown exception
 */
public class SessionException extends ServiceException {
    public SessionException(String message) {
        super(message);
    }
}
