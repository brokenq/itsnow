package dnt.itsnow.Exception;

import dnt.itsnow.platform.service.ServiceException;

/**
 * <h1>类功能说明</h1>
 */
public class WorkflowException extends ServiceException {
    public WorkflowException(String message) {
        super(message);
    }
}