/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.model;

import dnt.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RepeatValidator implements ConstraintValidator<RepeatPassword, PasswordContainer> {
    @Override
    public void initialize(RepeatPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(PasswordContainer cr, ConstraintValidatorContext context) {
        return StringUtils.equals(cr.getPassword(), cr.getRepeatPassword());
    }

}
