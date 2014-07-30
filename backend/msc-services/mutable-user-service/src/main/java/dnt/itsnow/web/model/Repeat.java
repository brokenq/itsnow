/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.web.model;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * 密码重复性校验器
  */
@Constraint(validatedBy = {RepeatValidator.class})
@Documented
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Repeat {
}

