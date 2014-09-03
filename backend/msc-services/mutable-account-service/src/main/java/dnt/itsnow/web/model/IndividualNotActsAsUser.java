/**
 * @author XiongJie, Date: 14-9-3
 */
package dnt.itsnow.web.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <h1>限制个人帐户扮演Service User的角色</h1>
 */
@Constraint(validatedBy = {IndividualValidator.class})
@Documented
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IndividualNotActsAsUser {
    String message() default "{dnt.itsnow.model.IsNotReservedDomain.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
