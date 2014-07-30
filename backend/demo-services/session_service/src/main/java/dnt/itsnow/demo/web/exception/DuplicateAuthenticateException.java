/**
 * Developer: Kadvin Date: 14-7-16 下午4:12
 */
package dnt.itsnow.demo.web.exception;

import dnt.itsnow.platform.web.exception.WebClientSideException;
import org.springframework.http.HttpStatus;

/**
 * 重复登录错误
 */
public class DuplicateAuthenticateException extends WebClientSideException {
    public DuplicateAuthenticateException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
