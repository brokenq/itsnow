/**
 * xiongjie on 14-8-7.
 */
package dnt.itsnow.model;

import org.junit.Before;
import org.junit.Test;
import junit.framework.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * <h1>账户模型的测试</h1>
 *
 * 这种模型测试主要包括两大方面：
 * <ul>
 * <li>模型完整性</li>
 * <li>业务方法</li>
 * </ul>
 */
public class AccountTest {
    static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    static Validator validator = factory.getValidator();

    Account account;

    @Before
    public void setUp() throws Exception {
        account = new MsuAccount();
    }

    @Test
    public void testEmptyAccountIsNotValid() throws Exception {
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testCorrectAccountIsValid() throws Exception {
        account.setSn("msu-001");
        account.setName("Test");
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        Assert.assertTrue(violations == null || violations.isEmpty());
    }

    @Test
    public void testApply() throws Exception {
        Account another = new MsuAccount();
        another.setSn("msu-010");
        another.setName("another-msu");
        account.apply(another);
        Assert.assertEquals("msu-010", account.getSn());
        Assert.assertEquals("another-msu", account.getName());
    }
}
