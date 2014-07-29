/**
 * Developer: Kadvin Date: 14-6-26 下午1:57
 */
package dnt.itsnow.platform.web.controller;

import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/** The Rest Controller */
@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ApplicationController {
    // 用于对控制器层的模型进行校验的对象
    protected static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    // 父类内部使用的headers
    private static   HttpHeaders      headers          = new HttpHeaders();
    // 提供给子类使用的通用的日志
    protected        Logger           logger           = LoggerFactory.getLogger(getClass());

    public ResponseEntity<Object> handleWebClientSideException(WebClientSideException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), headers, ex.getStatusCode(), request);
    }

    @ExceptionHandler(WebServerSideException.class)
    public ResponseEntity<Object> handleWebServerSideException(WebServerSideException ex, WebRequest request) {
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
