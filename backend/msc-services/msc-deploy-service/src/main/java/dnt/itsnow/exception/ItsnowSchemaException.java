/**
 * Developer: Kadvin Date: 14-9-15 下午9:01
 */
package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * Itsnow schema exception
 */
public class ItsnowSchemaException extends ServiceException {
    public ItsnowSchemaException(String message) {
        super(message);
    }

    public ItsnowSchemaException(String message, Exception cause) {
        super(message);
        initCause(cause);
    }
}
