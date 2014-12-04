package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * <h1>自定义异常类</h1>
 */
public class GroupException extends ServiceException {
    public GroupException(String message) {
        super(message);
    }
}
