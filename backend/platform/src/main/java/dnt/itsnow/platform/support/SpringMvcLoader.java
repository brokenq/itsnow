/**
 * Developer: Kadvin Date: 14-6-26 上午10:58
 */
package dnt.itsnow.platform.support;

import dnt.itsnow.platform.web.SpringMvcConfig;
import org.fusesource.scalate.servlet.TemplateEngineFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

/**
 * 代替WEB-INF/web.xml以程序方式初始化WEB APP的对象
 */

public class SpringMvcLoader extends AbstractAnnotationConfigDispatcherServletInitializer {
    public static final String SERVLET_VIEW_NAME = "ItsNow View";
    ApplicationContext applicationContext;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        applicationContext = (ApplicationContext) servletContext.getAttribute("application");
        super.onStartup(servletContext);
        registerScalateServlet(servletContext);
    }

    protected WebApplicationContext createServletApplicationContext() {
		AnnotationConfigWebApplicationContext servletAppContext =
                (AnnotationConfigWebApplicationContext) super.createServletApplicationContext();
        if( applicationContext != null ){
            servletAppContext.setParent(applicationContext);
            ApplicationEventForwarder forwarder = applicationContext.getBean(ApplicationEventForwarder.class);
            forwarder.bind(servletAppContext);
        }

        return servletAppContext;
	}

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{SpringMvcConfig.class};
    }

    @Override
    protected String getServletName() {
        return "ItsNow Platform";
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    protected void registerScalateServlet(ServletContext servletContext) {
        FilterRegistration.Dynamic registration = servletContext.addFilter(SERVLET_VIEW_NAME, TemplateEngineFilter.class);
        registration.addMappingForUrlPatterns(getDispatcherTypes(), false, "/views/*");
    }

    EnumSet<DispatcherType> getDispatcherTypes() {
   		return isAsyncSupported() ?
   			EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC) :
   			EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE);
   	}

}
