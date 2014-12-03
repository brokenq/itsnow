/**
 * Developer: Kadvin Date: 14-9-15 下午2:22
 */
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import dnt.itsnow.util.DeployFixture;
import net.happyonroad.support.JsonSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * 测试 Itsnow Schema 模型
 */
public class ItsnowSchemaTest extends ValidatorSupport {
    ItsnowSchema schema;

    @Before
    public void setUp() throws Exception {
        schema = DeployFixture.testSchema();
        schema.setHost(DeployFixture.testHost());
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

/*
    @Test
    public void testRequireHostId() throws Exception {
        schema.setHostId(null);
        Set<ConstraintViolation<ItsnowSchema>> violations = validator.validate(schema);
        Assert.assertFalse(violations.isEmpty());
    }
*/

    @Test
    public void testSerialization() throws Exception {
        String json = JsonSupport.toJSONString(schema);
        System.out.println(json);
        ItsnowSchema parsed = JsonSupport.parseJson(json, ItsnowSchema.class);
        Assert.assertEquals(schema, parsed);
    }
}
