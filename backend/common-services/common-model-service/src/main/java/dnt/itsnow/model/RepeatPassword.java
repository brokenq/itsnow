/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 密码重复性校验器
  */
@Constraint(validatedBy = {RepeatValidator.class})
@Documented
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatPassword {
    String message() default "{dnt.itsnow.model.RepeatPassword.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

