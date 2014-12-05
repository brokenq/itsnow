/**
 * @author XiongJie, Date: 14-9-3
 */
package dnt.itsnow.web.model;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import net.happyonroad.support.JsonSupport;
import org.junit.Assert;
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
        user.setRepeatPassword("123456");
        user.setPhone("138202020202");
        user.setEmail("jay@xiong.com");
        registration.setUser(user);
    }

    @Test
    public void testValidation() throws Exception {
        Set<ConstraintViolation<AccountRegistration>> violations = validator.validate(registration);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testJsonSerialize() throws Exception {
        String json = JsonSupport.toJSONString(registration);
        AccountRegistration parsed = JsonSupport.parseJson(json, AccountRegistration.class);
        Assert.assertEquals(registration, parsed);
    }

    @Test
    public void testSerializationFromClient() throws Exception {
        String json = "{\"type\":\"Enterprise\",\"asUser\":true,\"asProvider\":false,\"account\":{\"id\":null,\"createdAt\":null,\"updatedAt\":null,\"name\":\"it-account\",\"description\":null,\"type\":\"base\",\"sn\":null,\"domain\":\"it-domain\",\"userId\":null,\"status\":null},\"user\":{\"id\":null,\"createdAt\":null,\"updatedAt\":null,\"name\":\"it-user\",\"description\":null,\"email\":\"test@it.com\",\"phone\":\"12345678901\",\"password\":\"123456\",\"repeatPassword\":\"123456\",\"accountId\":null,\"enabled\":false,\"expired\":false,\"locked\":false,\"passwordExpired\":false,\"username\":\"it-user\"},\"attachments\":null}";
        JsonSupport.parseJson(json, AccountRegistration.class);
    }

}
