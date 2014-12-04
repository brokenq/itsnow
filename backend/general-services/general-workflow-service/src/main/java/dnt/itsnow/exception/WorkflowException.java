package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * <h1>类功能说明</h1>
 */
public class WorkflowException extends ServiceException {
    public WorkflowException(String message) {
        super(message);
    }
}