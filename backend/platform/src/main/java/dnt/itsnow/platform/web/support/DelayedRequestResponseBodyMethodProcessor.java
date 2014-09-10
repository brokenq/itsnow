/**
 * Developer: Kadvin Date: 14-9-10 上午11:31
 */
package dnt.itsnow.platform.web.support;

import dnt.itsnow.platform.web.annotation.AfterFilter;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.LinkedList;
import java.util.List;

/**
 * <h1>延迟执行方法Response Body解析</h1>
 *
 * 为了让AfterFilter对Response的修改生效，原本会写出的HttpMessageConverters 需要延迟到After Filter完成
 */
public class DelayedRequestResponseBodyMethodProcessor implements HandlerMethodReturnValueHandler {
    RequestResponseBodyMethodProcessor wrapped;
    List<Element>                      delayed;

    public DelayedRequestResponseBodyMethodProcessor(
            RequestResponseBodyMethodProcessor wrapped) {
        this.wrapped = wrapped;
        this.delayed = new LinkedList<Element>();
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return wrapped.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        BeforeFilter beforeFilter = AnnotationUtils.findAnnotation(returnType.getMethod(), BeforeFilter.class);
        AfterFilter afterFilter = AnnotationUtils.findAnnotation(returnType.getMethod(), AfterFilter.class);
        if (beforeFilter != null || afterFilter != null) {
            return;
        }
        Element call = new Element();
        call.returnValue = returnValue;
        call.returnType = returnType;
        call.mavContainer = mavContainer;
        call.webRequest = webRequest;
        this.delayed.add(call);
        // 防止 dispatcher servlet 自动寻找 view
        mavContainer.setRequestHandled(true);
    }

    public void handleAfterDelay() throws Exception {
        try {
            for (Element call : this.delayed) {
                wrapped.handleReturnValue(call.returnValue, call.returnType, call.mavContainer, call.webRequest);
            }
        } finally {
            this.delayed.clear();
        }
    }

    static class Element{
        Object                returnValue;
        MethodParameter       returnType;
        ModelAndViewContainer mavContainer;
        NativeWebRequest      webRequest;
    }
}
