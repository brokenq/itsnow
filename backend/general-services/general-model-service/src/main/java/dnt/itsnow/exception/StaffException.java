package dnt.itsnow.exception;

import dnt.itsnow.platform.service.ServiceException;

/**
 * <h1>类功能说明</h1>
 */
public class StaffException extends ServiceException {
    public StaffException(String message) {
        super(message);
    }
}