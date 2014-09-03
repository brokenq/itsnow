/**
 * @author XiongJie, Date: 14-9-3
 */
package dnt.itsnow.web.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <h1>Validate the account registration as enterprise don't play as User and Provider both</h1>
 */
public class EnterpriseValidator implements ConstraintValidator<EnterpriseNotActsAsBoth, AccountRegistration> {
    @Override
    public void initialize(EnterpriseNotActsAsBoth constraintAnnotation) {

    }

    @Override
    public boolean isValid(AccountRegistration registration, ConstraintValidatorContext context) {
        return registration.getType() != RegistrationType.Enterprise ||
               !(registration.isAsUser() && registration.isAsProvider());
    }
}
