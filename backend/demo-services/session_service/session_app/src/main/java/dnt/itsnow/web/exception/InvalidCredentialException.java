/**
 * Developer: Kadvin Date: 14-7-10 下午9:20
 */
package dnt.itsnow.web.exception;

import dnt.itsnow.platform.web.exception.WebClientSideException;
import org.springframework.http.HttpStatus;

/**
 * 登录时用户名密码输入错误
 */
public class InvalidCredentialException extends WebClientSideException {
    public InvalidCredentialException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
