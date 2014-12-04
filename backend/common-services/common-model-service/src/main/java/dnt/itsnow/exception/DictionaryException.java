package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * <h1>流程字典自定义异常类</h1>
 */
public class DictionaryException extends ServiceException {
    public DictionaryException(String message) {
        super(message);
    }
}
