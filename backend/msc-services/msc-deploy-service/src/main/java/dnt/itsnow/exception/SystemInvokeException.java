/**
 * Developer: Kadvin Date: 14-9-15 下午5:08
 */
package dnt.itsnow.exception;

import dnt.itsnow.platform.service.ServiceException;

/**
 * 系统调用的异常
 */
public class SystemInvokeException extends ServiceException {
    public SystemInvokeException(String message) {
        super(message);
    }
}
