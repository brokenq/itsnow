/**
 * Developer: Kadvin Date: 14-9-15 下午2:22
 */
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import dnt.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Properties;
import java.util.Set;

/**
 * 测试 Itsnow Host 模型
 */
public class ItsnowHostTest extends ValidatorSupport {
    ItsnowHost host;

    @Before
    public void setUp() throws Exception {
        host = new ItsnowHost();
        host.setName("TestHost");
        host.setAddress("192.168.0.10");
        Properties configuration = new Properties();
        configuration.setProperty("username", "root");
        configuration.setProperty("password", "root123");
        host.setConfiguration(configuration);
    }

    @Test
    public void testHappyCase() throws Exception {
        Set<ConstraintViolation<ItsnowHost>> violations = validator.validate(host);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testRequireName() throws Exception {
        host.setName(null);
        Set<ConstraintViolation<ItsnowHost>> violations = validator.validate(host);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testRequireAddress() throws Exception {
        host.setAddress(null);
        Set<ConstraintViolation<ItsnowHost>> violations = validator.validate(host);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testSerialization() throws Exception {
        String json = JsonSupport.toJSONString(host);
        System.out.println(json);
        ItsnowHost parsed = JsonSupport.parseJson(json, ItsnowHost.class);
        Assert.assertEquals(host, parsed);
    }
}
