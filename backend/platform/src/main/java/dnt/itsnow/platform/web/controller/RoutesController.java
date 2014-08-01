/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.platform.web.controller;

import dnt.itsnow.platform.web.model.RouteItem;
import dnt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * <h1>用于显示平台中所有已经映射的路由</h1>
 */
@RestController
@RequestMapping("/routes")
public class RoutesController extends ApplicationController{
    @Autowired RequestMappingHandlerMapping mappings;

    /**
     *
     * <h2>返回所有的路由信息</h2>
     *
     * GET /routes
     *
     * @param method  请求方法
     * @param pattern url特征
     * @param detail  是否显示映射的方法，默认不显示
     * @return 请求路由表 格式化好的字符串
     */
    @RequestMapping
    public String list(@RequestParam(value = "method", required = false, defaultValue = "") String method,
                       @RequestParam(value = "pattern", required = false, defaultValue = "") String pattern,
                       @RequestParam(value = "detail", required = false, defaultValue = "false") boolean detail
                       ) {
        Map<RequestMappingInfo, HandlerMethod> methods = mappings.getHandlerMethods();
        List<RouteItem> routeItems = new ArrayList<RouteItem>(methods.size());
        for( RequestMappingInfo mapping : methods.keySet()){
            String httpMethods = mapping.getMethodsCondition().toString();
            httpMethods = StringUtils.substringBetween(httpMethods,"[","]");
            if( httpMethods.equals("") ) httpMethods = "GET";
            if( !StringUtils.containsIgnoreCase(httpMethods, method) ) continue;
            String url = mapping.getPatternsCondition().toString();
            url = StringUtils.substringBetween(url, "[", "]");
            if( !StringUtils.containsIgnoreCase(url, pattern) ) continue;
            HandlerMethod handler = methods.get(mapping);
            Map<String,String> requestParams = new HashMap<String,String>();
            for (MethodParameter parameter : handler.getMethodParameters()) {
                RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
                if(requestParam != null) requestParams.put(requestParam.value(), parameter.getParameterType().getSimpleName());
            }
            //固定的把 Application Controller 对 index 的 before filter增强加入到路由表达里面
            // 照理来说，应该根据每个handler的method，找到其所有before/after filter，将相关filter的request params加入展示
            // 现在先采用这个权宜之计
            if( httpMethods.contains("GET") && handler.toString().contains("index") ){
                requestParams.put("page", "int");
                requestParams.put("size", "int");
                requestParams.put("sort", "string");
            }
            RouteItem item = new RouteItem(httpMethods, url, handler.toString(), requestParams);
            item.showDetail(detail);
            routeItems.add(item);
        }
        Collections.sort(routeItems);
        return StringUtils.join(routeItems, "\r\n");
    }


}
