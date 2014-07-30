/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.platform.web.annotation;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 用户标记特定方法需要在请求之前执行
 */
@Documented
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterFilter {
    /** 过滤器顺序，越小越靠前执行 */
    int order() default 50;

    /**
     * 过滤器适用的http方法，默认为空，说明适用于任何http方法
     *
     * @return 过滤器适用的http方法
     */
    RequestMethod[] method() default {};

    /**
     * 过滤器适用的method名称，默认为空，说明适用于任何methods
     *
     * @return 过滤器适用的method名称, 不需要签名和返回值信息
     */
    String[] value() default {};
}
