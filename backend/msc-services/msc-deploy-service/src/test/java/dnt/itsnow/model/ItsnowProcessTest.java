/**
 * Developer: Kadvin Date: 14-9-15 下午2:23
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
 * 测试 Itsnow Process 模型
 */
public class ItsnowProcessTest extends ValidatorSupport{
    ItsnowProcess process;

    @Before
    public void setUp() throws Exception {
        process = new ItsnowProcess();
        process.setName("TestProcess");
        process.setAccountId(1L);
        process.setHostId(1L);
        process.setSchemaId(1L);
        process.setWd("/opt/releases/itsnow");
        Properties configuration = new Properties();
        configuration.setProperty("jmx.port", "8071");
        configuration.setProperty("debug.port", "1071");
        configuration.setProperty("rmi.port", "8098");
        process.setConfiguration(configuration);
    }

    @Test
    public void testHappyCase() throws Exception {
        Set<ConstraintViolation<ItsnowProcess>> violations = validator.validate(process);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testRequireName() throws Exception {
        process.setName(null);
        Set<ConstraintViolation<ItsnowProcess>> violations = validator.validate(process);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testRequireWd() throws Exception {
        process.setWd(null);
        Set<ConstraintViolation<ItsnowProcess>> violations = validator.validate(process);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testRequireAccountId() throws Exception {
        process.setAccountId(null);
        Set<ConstraintViolation<ItsnowProcess>> violations = validator.validate(process);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testRequireHostId() throws Exception {
        process.setHostId(null);
        Set<ConstraintViolation<ItsnowProcess>> violations = validator.validate(process);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testRequireSchemaId() throws Exception {
        process.setSchemaId(null);
        Set<ConstraintViolation<ItsnowProcess>> violations = validator.validate(process);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testSerialization() throws Exception {
        String json = JsonSupport.toJSONString(process);
        System.out.println(json);
        ItsnowProcess parsed = JsonSupport.parseJson(json, ItsnowProcess.class);
        Assert.assertEquals(process, parsed);
    }
}
