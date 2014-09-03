/**
 * @author XiongJie, Date: 14-9-3
 */
package dnt.itsnow.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * <h1> Validator </h1>
 */
public class ReservedDomainValidator implements ConstraintValidator<IsNotReservedDomain, String>{
    @Override
    public void initialize(IsNotReservedDomain constraintAnnotation) {

    }

    @Override
    public boolean isValid(String domain, ConstraintValidatorContext context) {
        return Arrays.binarySearch(Account.RESERVED_DOMAINS, domain) < 0;
    }
}
