/**
 * @author XiongJie, Date: 14-9-3
 */
package dnt.itsnow.web.model;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * <h1>测试 AccountRegistration 这个Web模型是否可以正常工作 </h1>
 */
public class AccountRegistrationTest {
    static ValidatorFactory factory   = Validation.buildDefaultValidatorFactory();
    static Validator        validator = factory.getValidator();

    AccountRegistration registration;

    @Before
    public void setUp() throws Exception {
        registration = new AccountRegistration();
        registration.setType(RegistrationType.Enterprise);
        registration.setAsProvider(true);

        Account account = new Account();
        account.setName("test-account");
        account.setDomain("test-domain");
        account.setDescription("test account");
        registration.setAccount(account);

        User user = new User();
        user.setUsername("jay.xiong");
        user.setPassword("123456");
        user.setPhone("138202020202");
        user.setEmail("jay@xiong.com");
        registration.setUser(user);
    }

    @Test
    public void testValidation() throws Exception {
        Set<ConstraintViolation<AccountRegistration>> violations = validator.validate(registration);
        Assert.assertTrue(violations.isEmpty());
    }
}
