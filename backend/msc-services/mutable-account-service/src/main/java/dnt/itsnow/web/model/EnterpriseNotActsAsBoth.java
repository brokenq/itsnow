/**
 * @author XiongJie, Date: 14-9-3
 */
package dnt.itsnow.web.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <h1>限制企业账户扮演两个角色</h1>
 * 暂时不支持企业账户同时扮演两个角色
 */
@Constraint(validatedBy = {EnterpriseValidator.class})
@Documented
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnterpriseNotActsAsBoth {
    String message() default "{dnt.itsnow.model.IsNotReservedDomain.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
