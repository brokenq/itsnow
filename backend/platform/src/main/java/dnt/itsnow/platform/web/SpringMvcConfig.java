/**
 * Developer: Kadvin Date: 14-6-26 上午11:04
 */
package dnt.itsnow.platform.web;

import dnt.itsnow.platform.web.interceptor.AfterFilterInterceptor;
import dnt.itsnow.platform.web.interceptor.BeforeFilterInterceptor;
import dnt.itsnow.platform.web.support.ExtendedRequestMappingHandlerMapping;
import org.fusesource.scalate.spring.view.ScalateViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 代替一般WebApp中的web-context-xml 以Annotation方式定义Spring MVC
 */
@Configuration
//@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("dnt.itsnow.platform.web.controller")
public class SpringMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Bean
    public ScalateViewResolver scalateViewResolver(){
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
   		if(configurer.isUseSuffixPatternMatch() != null) {
   			handlerMapping.setUseSuffixPatternMatch(configurer.isUseSuffixPatternMatch());
   		}
   		if(configurer.isUseRegisteredSuffixPatternMatch() != null) {
   			handlerMapping.setUseRegisteredSuffixPatternMatch(configurer.isUseRegisteredSuffixPatternMatch());
   		}
   		if(configurer.isUseTrailingSlashMatch() != null) {
   			handlerMapping.setUseTrailingSlashMatch(configurer.isUseTrailingSlashMatch());
   		}
   		if(configurer.getPathMatcher() != null) {
   			handlerMapping.setPathMatcher(configurer.getPathMatcher());
   		}
   		if(configurer.getUrlPathHelper() != null) {
   			handlerMapping.setUrlPathHelper(configurer.getUrlPathHelper());
   		}
   		return handlerMapping;
   	}

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        RequestMappingHandlerAdapter adapter = requestMappingHandlerAdapter();
        registry.addInterceptor(new BeforeFilterInterceptor(adapter));
        registry.addInterceptor(new AfterFilterInterceptor(adapter));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //前端的静态资源映射
        registry.addResourceHandler("/**").addResourceLocations("/build/", "/deploy/", "/public/");
        //favicon映射
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/build/assets/favicon.ico","/deploy/images/favicon.ico","/public/images/favicon.ico");
    }


}
