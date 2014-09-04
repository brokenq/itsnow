/**
 * Developer: Kadvin Date: 14-9-4 下午1:45
 */
package dnt.itsnow.test.model;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * The validator support for model test
 */
public abstract class ValidatorSupport {
    protected static ValidatorFactory factory   = Validation.buildDefaultValidatorFactory();
    protected static Validator        validator = factory.getValidator();
}
