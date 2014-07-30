/**
 * Developer: Kadvin Date: 14-7-16 上午9:50
 */
package dnt.itsnow.platform.web.support;

import dnt.itsnow.platform.services.ServicePackageEvent;
import dnt.itsnow.platform.web.controller.ApplicationController;
import net.happyonroad.component.core.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.HandlerMethodSelector;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <h1>被扩展的Spring Request Mapping</h1>
 * 主要目的是让插入的服务scan出来的controller的request mapping 方法能注册上来
 */
public class ExtendedRequestMappingHandlerMapping extends RequestMappingHandlerMapping
        implements ApplicationListener<ServicePackageEvent> {

    private ApplicationContext theApplicationContext;

    @Override
    public void onApplicationEvent(ServicePackageEvent event) {
        if (event instanceof ServicePackageEvent.LoadedEvent) {
            Component component = event.getSource();
            ApplicationContext application = component.getApplication();
            theApplicationContext = application;
            if( application == null ) return;// it's not a component with application
            // controllers 是注册在parent中的
            String[] controllerNames = application.getBeanNamesForType(ApplicationController.class);
            for (String controllerName : controllerNames) {
                detectHandlerMethods(controllerName);
            }
            theApplicationContext = null;
        }
    }

    ApplicationContext theApplicationContext() {
        return theApplicationContext == null ? getApplicationContext() : theApplicationContext;
    }

    /**
   	 * Look for handler methods in a handler.
   	 * @param handler the bean name of a handler or a handler instance
   	 */
   	protected void detectHandlerMethods(final Object handler) {
   		Class<?> handlerType = (handler instanceof String ? theApplicationContext().getType((String) handler) :
                                handler.getClass());

   		// Avoid repeated calls to getMappingForMethod which would rebuild RequestMappingInfo instances
   		final Map<Method, RequestMappingInfo> mappings = new IdentityHashMap<Method, RequestMappingInfo>();
   		final Class<?> userType = ClassUtils.getUserClass(handlerType);

   		Set<Method> methods = HandlerMethodSelector.selectMethods(userType, new ReflectionUtils.MethodFilter() {
               @Override
               public boolean matches(Method method) {
                   RequestMappingInfo mapping = getMappingForMethod(method, userType);
                   if (mapping != null) {
                       mappings.put(method, mapping);
                       return true;
                   } else {
                       return false;
                   }
               }
           });

   		for (Method method : methods) {
   			registerHandlerMethod(handler, method, mappings.get(method));
   		}
   	}

    /**
   	 * Create the HandlerMethod instance.
   	 * @param handler either a bean name or an actual handler instance
   	 * @param method the target method
   	 * @return the created HandlerMethod
   	 */
   	protected HandlerMethod createHandlerMethod(Object handler, Method method) {
   		HandlerMethod handlerMethod;
   		if (handler instanceof String) {
   			String beanName = (String) handler;
   			handlerMethod = new HandlerMethod(beanName, theApplicationContext(), method);
   		}
   		else {
   			handlerMethod = new HandlerMethod(handler, method);
   		}
   		return handlerMethod;
   	}


}
