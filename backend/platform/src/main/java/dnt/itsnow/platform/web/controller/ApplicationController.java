/**
 * Developer: Kadvin Date: 14-6-26 下午1:57
 */
package dnt.itsnow.platform.web.controller;

import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.PageRequest;
import dnt.itsnow.platform.service.Sort;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

/** The Rest Controller */
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ApplicationController {
    // 父类内部使用的headers
    private static   HttpHeaders      headers          = new HttpHeaders();
    // 提供给子类使用的通用的日志
    protected        Logger           logger           = LoggerFactory.getLogger(getClass());

    // 通过 Before Filter 自动创建的page request对象
    protected PageRequest pageRequest;

    @ExceptionHandler(WebClientSideException.class)
    public ResponseEntity<Object> handleWebClientSideException(WebClientSideException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), headers, ex.getStatusCode(), request);
    }

    @ExceptionHandler(WebServerSideException.class)
    public ResponseEntity<Object> handleWebServerSideException(WebServerSideException ex, WebRequest request) {
        logger.warn("Caught server side exception: " + ex.getMessage(), ex);
        return handleExceptionInternal(ex, ex.getMessage(), headers, ex.getStatusCode(), request);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleWebServerSideException(Throwable ex, WebRequest request) {
        logger.error("Caught unhandled exception: " + ex.getMessage(), ex);
        return handleExceptionInternal(ex, ex.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Throwable ex, String body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<Object>(body, headers, status);
    }

    /**
     * <h2>初始化默认的分页请求</h2>
     *
     * 注意：这个请求仅仅针对 GET类型动作，实现方法名称为index的生效
     *
     * @param page    第几页
     * @param size    分页参数
     *                即便这个值被放到用户profile,或者session里面
     *                那也是前端程序读取到这个值，而后传递过来，而不是这里去读取
     * @param sort 排序参数
     */
    @BeforeFilter(method = RequestMethod.GET, value = "index")
    public void initDefaultPageRequest( @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                        @RequestParam(required = false, value = "size", defaultValue = "40") int size,
                                        @RequestParam(required = false, value = "sort", defaultValue = "") String sort){
        Sort theSort = parseSort(sort);
        pageRequest = new PageRequest(page, size, theSort);
    }

    private Sort parseSort(String sort) {
        // TODO PARSE SORT correctly
        return null;// new Sort(sort);
    }

    protected void renderHeader(HttpServletResponse response, Page page) {
        response.setHeader(Page.TOTAL, String.valueOf(page.getTotalElements()));
        response.setHeader(Page.PAGES, String.valueOf(page.getTotalPages()));
        response.setHeader(Page.NUMBER, String.valueOf(page.getNumber()));
        response.setHeader(Page.REAL, String.valueOf(page.getNumberOfElements()));
        response.setHeader(Page.SORT, String.valueOf(page.getSort()));
    }
}
