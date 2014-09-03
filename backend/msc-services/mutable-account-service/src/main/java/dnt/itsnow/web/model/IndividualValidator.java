/**
 * @author XiongJie, Date: 14-9-3
 */
package dnt.itsnow.web.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <h1>Validate the account registration as individual don't play as User</h1>
 */
public class IndividualValidator implements ConstraintValidator<IndividualNotActsAsUser, AccountRegistration> {
    @Override
    public void initialize(IndividualNotActsAsUser constraintAnnotation) {

    }

    @Override
    public boolean isValid(AccountRegistration registration, ConstraintValidatorContext context) {
        return registration.getType() != RegistrationType.Individual || !registration.isAsUser();
    }
}
