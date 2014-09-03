/**
 * @author XiongJie, Date: 14-9-3
 */
package dnt.itsnow.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <h1>用于判断account的domain是否是保留域名</h1>
 */
@Documented
@Constraint(validatedBy = ReservedDomainValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface IsNotReservedDomain {
    String message() default "{dnt.itsnow.model.IsNotReservedDomain.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
