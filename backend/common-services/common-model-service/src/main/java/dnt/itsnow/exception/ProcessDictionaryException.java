package dnt.itsnow.exception;

import dnt.itsnow.platform.service.ServiceException;

/**
 * <h1>流程字典自定义异常类</h1>
 */
public class ProcessDictionaryException extends ServiceException {
    public ProcessDictionaryException(String message) {
        super(message);
    }
}
