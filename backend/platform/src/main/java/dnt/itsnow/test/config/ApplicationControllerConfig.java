/**
 * xiongjie on 14-8-7.
 */
package dnt.itsnow.test.config;

import dnt.itsnow.platform.web.interceptor.AfterFilterInterceptor;
import dnt.itsnow.platform.web.interceptor.BeforeFilterInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * <h1>支持对ApplicationController的子类进行测试</h1>
 *
 * 主要是将 平台设定的 @BeforeFilter, @AfterFilter机制注入到 Web App Context 中
 */
public class ApplicationControllerConfig extends WebMvcConfigurationSupport {
    // interceptors
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        RequestMappingHandlerAdapter adapter = requestMappingHandlerAdapter();
        registry.addInterceptor(new BeforeFilterInterceptor(adapter));
        registry.addInterceptor(new AfterFilterInterceptor(adapter));
    }
}
