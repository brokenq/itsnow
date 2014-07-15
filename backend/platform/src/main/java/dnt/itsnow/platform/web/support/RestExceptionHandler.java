/**
 * Developer: Kadvin Date: 14-7-11 下午1:18
 */
package dnt.itsnow.platform.web.support;

import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The RESTful API Exception handler
 */
@Component
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(WebClientSideException.class)
    public void handleWebClientSideException(WebClientSideException ex, WebRequest request){
        HttpHeaders headers = new HttpHeaders();
        handleExceptionInternal(ex, ex.getMessage(), headers, ex.getStatusCode(), request);
    }

    @ExceptionHandler(WebClientSideException.class)
    public void handleWebServerSideException(WebServerSideException ex, WebRequest request){
        HttpHeaders headers = new HttpHeaders();
        handleExceptionInternal(ex, ex.getMessage(), headers, ex.getStatusCode(), request);
    }
}
