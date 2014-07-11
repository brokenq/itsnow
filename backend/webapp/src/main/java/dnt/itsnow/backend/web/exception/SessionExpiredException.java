/**
 * Developer: Kadvin Date: 14-7-10 下午10:12
 */
package dnt.itsnow.backend.web.exception;

import org.springframework.http.HttpStatus;

/**
 * 客户端会话过期错误
 */
public class SessionExpiredException extends WebClientSideException {
    public SessionExpiredException(String statusText) {
        super(HttpStatus.UNAUTHORIZED, statusText);
    }
}
