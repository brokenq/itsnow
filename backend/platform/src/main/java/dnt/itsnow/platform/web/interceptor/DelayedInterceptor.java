/**
 * Developer: Kadvin Date: 14-9-10 下午3:17
 */
package dnt.itsnow.platform.web.interceptor;

import dnt.itsnow.platform.web.support.DelayedRequestResponseBodyMethodProcessor;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 在postHandle阶段完成对 DelayedRequestResponseBodyMethodProcessor 的调用
 */
public class DelayedInterceptor extends HandlerInterceptorAdapter {
    private final RequestMappingHandlerAdapter adapter;

    public DelayedInterceptor(RequestMappingHandlerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        DelayedRequestResponseBodyMethodProcessor delayed = null;
        List<HandlerMethodReturnValueHandler> handlers = adapter.getReturnValueHandlers();
        for (HandlerMethodReturnValueHandler rvHandler : handlers) {
            if( rvHandler instanceof DelayedRequestResponseBodyMethodProcessor){
                delayed = (DelayedRequestResponseBodyMethodProcessor) rvHandler;
                break;
            }
        }
        if( delayed != null ){
            delayed.handleAfterDelay();
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
