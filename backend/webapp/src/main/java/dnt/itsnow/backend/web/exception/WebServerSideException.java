/**
 * Developer: Kadvin Date: 14-7-11 下午1:22
 */
package dnt.itsnow.backend.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

/**
 * WEB层异常，异常源自服务端
 */
public class WebServerSideException extends HttpServerErrorException {
    public WebServerSideException(HttpStatus status, String message) {
        super(status, message);
    }
}
