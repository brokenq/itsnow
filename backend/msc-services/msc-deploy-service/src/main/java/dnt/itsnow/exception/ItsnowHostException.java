/**
 * Developer: Kadvin Date: 14-9-15 下午3:34
 */
package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * The host exception
 */
public class ItsnowHostException extends ServiceException {
    public ItsnowHostException(String message) {
        super(message);
    }

    public ItsnowHostException(String message, Exception cause) {
        super(message);
        initCause(cause);
    }
}
