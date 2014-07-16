/**
 * Developer: Kadvin Date: 14-6-26 下午1:57
 */
package dnt.itsnow.platform.web.controller;

import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * The Rest Controller
 */
@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ApplicationController {
    private static HttpHeaders headers = new HttpHeaders();

    @ExceptionHandler(WebClientSideException.class)
    public ResponseEntity<Object> handleWebClientSideException(WebClientSideException ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getMessage(), headers, ex.getStatusCode(), request);
    }

    @ExceptionHandler(WebServerSideException.class)
    public ResponseEntity<Object> handleWebServerSideException(WebServerSideException ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getMessage(), headers, ex.getStatusCode(), request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(HttpStatusCodeException ex, String body, HttpHeaders headers,
                                                           HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
      			request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
      		}

        return new ResponseEntity<Object>(body, headers, status);
    }
}
