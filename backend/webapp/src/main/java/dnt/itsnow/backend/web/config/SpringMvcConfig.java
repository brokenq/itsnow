/**
 * Developer: Kadvin Date: 14-6-26 上午11:04
 */
package dnt.itsnow.backend.web.config;

import org.fusesource.scalate.spring.view.ScalateViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 代替一般WebApp中的web-context-xml 以Annotation方式定义Spring MVC
 */
@Configuration
@EnableWebMvc
@ComponentScan({"dnt.itsnow.backend.web.controller","dnt.itsnow.backend.web.support"})
public class SpringMvcConfig extends WebMvcConfigurerAdapter {
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //资源先在 frontend中找(这是前端项目集成过来的)，而后到public里面找(这是后端项目的deploy|build过来的静态资源)
        registry.addResourceHandler("/**").addResourceLocations("/frontend/","/public/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/public/images/favicon.ico");
    }

}
