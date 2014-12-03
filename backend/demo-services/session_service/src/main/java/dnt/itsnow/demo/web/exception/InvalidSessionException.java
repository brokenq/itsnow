/**
 * Developer: Kadvin Date: 14-7-10 下午10:12
 */
package dnt.itsnow.demo.web.exception;

import net.happyonroad.platform.web.exception.WebClientSideException;
import org.springframework.http.HttpStatus;

/**
 * 客户端会话错误
 */
public class InvalidSessionException extends WebClientSideException {
    public InvalidSessionException(String statusText) {
        super(HttpStatus.UNAUTHORIZED, statusText);
    }
}
