package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * <h1>工作时间自定义异常类</h1>
 */
public class WorkTimeException extends ServiceException {
    public WorkTimeException(String message) {
        super(message);
    }
}
