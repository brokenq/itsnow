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
 * 测试 Itsnow Schema 模型
 */
public class ItsnowSchemaTest extends ValidatorSupport {
    ItsnowSchema schema;

    @Before
    public void setUp() throws Exception {
        schema = new ItsnowSchema();
        schema.setName("TestSchema");
        schema.setHostId(1);
        Properties configuration = new Properties();
        configuration.setProperty("db.user", "itsnow");
        configuration.setProperty("db.pwd", "secret");
        schema.setConfiguration(configuration);
    }

    @Test
    public void testHappyCase() throws Exception {
        Set<ConstraintViolation<ItsnowSchema>> violations = validator.validate(schema);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testRequireName() throws Exception {
        schema.setName(null);
        Set<ConstraintViolation<ItsnowSchema>> violations = validator.validate(schema);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testRequireHostId() throws Exception {
        schema.setHostId(null);
        Set<ConstraintViolation<ItsnowSchema>> violations = validator.validate(schema);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testSerialization() throws Exception {
        String json = JsonSupport.toJSONString(schema);
        System.out.println(json);
        ItsnowSchema parsed = JsonSupport.parseJson(json, ItsnowSchema.class);
        Assert.assertEquals(schema, parsed);
    }
}
