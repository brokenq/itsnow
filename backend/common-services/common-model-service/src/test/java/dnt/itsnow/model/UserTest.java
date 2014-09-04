/**
 * Developer: Kadvin Date: 14-9-4 下午1:44
 */
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import dnt.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Test the user
 */
public class UserTest extends ValidatorSupport{
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setEmail("test@test.com");
        user.setName("test");
        user.setPhone("123202020202");
        user.setPassword("123456");
        user.setRepeatPassword("123456");
    }

    @Test
    public void testValidationHappyCase() throws Exception {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertTrue(violations.isEmpty());
    }

    // TODO add other unhappy validation cases

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(user);
        System.out.println(json);
        User parsed = JsonSupport.parseJson(json, User.class);
        Assert.assertEquals(user, parsed);
    }

}
