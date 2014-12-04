package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * <h1>部门自定义异常类</h1>
 */
public class DepartmentException extends ServiceException {
    public DepartmentException(String message) {
        super(message);
    }
}