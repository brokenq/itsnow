package dnt.itsnow.exception;

import dnt.itsnow.platform.service.ServiceException;

/**
 * <h1>自定义异常类</h1>
 */
public class GroupException extends ServiceException {
    public GroupException(String message) {
        super(message);
    }
}
