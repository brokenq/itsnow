
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import dnt.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.sql.Timestamp;
import java.util.Set;

/**
 * <h1>故障模型的测试</h1>
 *
 * 这种模型测试主要包括两大方面：
 * <ul>
 * <li>模型完整性</li>
 * <li>JSON序列化</li>
 * <li>业务方法</li>
 * </ul>
 */
public class IncidentTest extends ValidatorSupport{

    Incident incident;

    @Before
    public void setUp() throws Exception {
        incident = new Incident();
        incident.setId(1L);
        incident.setRequestDescription("System halt");
        //incident.setRequesterName("admin");
        incident.setCreatedBy("admin");
        incident.setMspStatus(IncidentStatus.New);
        incident.setNumber("INC20140915203300001");
        incident.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setUpdatedAt(incident.getCreatedAt());
    }

    @Test
    @Ignore
    public void testNumberConstraints() throws Exception {
        incident.setNumber("");
        Set<ConstraintViolation<Incident>> violations = validator.validate(incident);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testDescriptionConstraints() throws Exception {
        incident.setRequestDescription("");
        Set<ConstraintViolation<Incident>> violations = validator.validate(incident);
        Assert.assertFalse(violations.isEmpty());

    }

    @Test
    public void testRequestNameConstraints() throws Exception{
        incident.setRequesterName("");
        Set<ConstraintViolation<Incident>> violations = validator.validate(incident);
        Assert.assertFalse(violations.isEmpty());
    }


    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(incident);
        System.out.println(json);
        Incident parsed = JsonSupport.parseJson(json, Incident.class);
        Assert.assertEquals(incident, parsed);
    }
}
