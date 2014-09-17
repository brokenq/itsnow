/**
 * Developer: Kadvin Date: 14-6-26 上午11:04
 */
package dnt.itsnow.platform.web;

import dnt.itsnow.platform.web.interceptor.AfterFilterInterceptor;
import dnt.itsnow.platform.web.interceptor.BeforeFilterInterceptor;
import dnt.itsnow.platform.web.interceptor.DelayedInterceptor;
import dnt.itsnow.platform.web.support.DelayedRequestResponseBodyMethodProcessor;
import dnt.itsnow.platform.web.support.ExtendedRequestMappingHandlerMapping;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fusesource.scalate.spring.view.ScalateViewResolver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

/**
 * 代替一般WebApp中的web-context-xml 以Annotation方式定义Spring MVC
 */
@Configuration
//@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("dnt.itsnow.platform.web.controller")
public class SpringMvcConfig extends WebMvcConfigurationSupport implements InitializingBean {
    public static final Log logger = LogFactory.getLog(SpringMvcConfig.class);

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Bean
    public ScalateViewResolver scalateViewResolver() {
        ScalateViewResolver resolver = new ScalateViewResolver();
        resolver.setPrefix("/views/");
        resolver.setSuffix(".jade");
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        PathMatchConfigurer configurer = new PathMatchConfigurer();
        configurePathMatch(configurer);
        ExtendedRequestMappingHandlerMapping handlerMapping = new ExtendedRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager());
        if (configurer.isUseSuffixPatternMatch() != null) {
            handlerMapping.setUseSuffixPatternMatch(configurer.isUseSuffixPatternMatch());
        }
        if (configurer.isUseRegisteredSuffixPatternMatch() != null) {
            handlerMapping.setUseRegisteredSuffixPatternMatch(configurer.isUseRegisteredSuffixPatternMatch());
        }
        if (configurer.isUseTrailingSlashMatch() != null) {
            handlerMapping.setUseTrailingSlashMatch(configurer.isUseTrailingSlashMatch());
        }
        if (configurer.getPathMatcher() != null) {
            handlerMapping.setPathMatcher(configurer.getPathMatcher());
        }
        if (configurer.getUrlPathHelper() != null) {
            handlerMapping.setUrlPathHelper(configurer.getUrlPathHelper());
   		}
        logger.debug("Customize Spring MVC with ExtendedRequestMappingHandlerMapping");
   		return handlerMapping;
   	}

    @Override
    public void afterPropertiesSet() throws Exception {
        RequestMappingHandlerAdapter adapter = this.requestMappingHandlerAdapter();
        //hacking the adapter returnValueHandlers
        // replace RequestResponseBodyMethodProcessor with Delayed RequestResponseBodyMethodProcessor
        // property path: adapter#returnValueHandlers#returnValueHandlers
        Object composite = FieldUtils.readField( adapter, "returnValueHandlers", true);
        //noinspection unchecked
        List<HandlerMethodReturnValueHandler> actual = (List<HandlerMethodReturnValueHandler>)FieldUtils.readField( composite, "returnValueHandlers", true);
        int index = -1;
        for (int i = 0; i < actual.size(); i++) {
            Object o = actual.get(i);
            if( o instanceof RequestResponseBodyMethodProcessor ){
                index = i; break;
            }
        }
        if( index == -1 )
            throw new IllegalStateException("Can't find any RequestResponseBodyMethodProcessor in requestMappingHandlerAdapter#handlers");
        RequestResponseBodyMethodProcessor wrapped = (RequestResponseBodyMethodProcessor) actual.get(index);
        DelayedRequestResponseBodyMethodProcessor delayed = new DelayedRequestResponseBodyMethodProcessor(wrapped);
        actual.set(index, delayed);
        logger.debug("Replace Spring MVC RequestResponseBodyMethodProcessor as delayed one");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        RequestMappingHandlerAdapter adapter = this.requestMappingHandlerAdapter();
        registry.addInterceptor(new DelayedInterceptor(adapter));
        registry.addInterceptor(new BeforeFilterInterceptor(adapter));
        registry.addInterceptor(new AfterFilterInterceptor(adapter));
        logger.debug("Add Before/After/Delayed Filter Interceptors");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //前端的静态资源映射
        registry.addResourceHandler("/**").addResourceLocations("/build/", "/deploy/", "/public/");
        //favicon映射
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/build/assets/favicon.ico","/deploy/images/favicon.ico","/public/images/favicon.ico");
    }


}
