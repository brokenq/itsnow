package dnt.itsnow.exception;

import dnt.itsnow.platform.service.ServiceException;

/**
 * <h1>自定义异常类</h1>
 */
public class MscRoleException extends ServiceException {
    public MscRoleException(String message) {
        super(message);
    }
}
