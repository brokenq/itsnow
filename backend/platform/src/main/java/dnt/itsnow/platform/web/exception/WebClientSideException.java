/**
 * Developer: Kadvin Date: 14-7-11 下午1:22
 */
package dnt.itsnow.platform.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * WEB层异常，异常源自客户端
 */
public class WebClientSideException extends HttpClientErrorException{
    public WebClientSideException(HttpStatus status, String message) {
        super(status, message);
    }
}
