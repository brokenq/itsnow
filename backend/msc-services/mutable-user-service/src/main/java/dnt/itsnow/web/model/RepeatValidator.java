/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.web.model;

import dnt.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class RepeatValidator implements ConstraintValidator<Repeat, ChangePasswordRequest> {
    @Override
    public void initialize(Repeat constraintAnnotation) {

    }

    @Override
    public boolean isValid(ChangePasswordRequest cr, ConstraintValidatorContext context) {
        return StringUtils.equals(cr.getOldPassword(), cr.getRepeatPassword());
    }

}
