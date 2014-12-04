/**
 * Developer: Kadvin Date: 14-9-15 上午11:07
 */
package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * <h1>The itsnow process exception</h1>
 */
public class ItsnowProcessException extends ServiceException {
    public ItsnowProcessException(String message) {
        super(message);
    }

    public ItsnowProcessException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
