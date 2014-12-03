/**
 * xiongjie on 14-8-7.
 */
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import net.happyonroad.support.JsonSupport;
import org.junit.Assert;
import org.activiti.engine.history.HistoricActivityInstance;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * <h1>MSU故障模型的测试</h1>
 *
 * 这种模型测试主要包括两大方面：
 * <ul>
 * <li>模型完整性</li>
 * <li>JSON序列化</li>
 * <li>业务方法</li>
 * </ul>
 */
public class MsuIncidentTest extends ValidatorSupport{

    MsuIncident msuIncident;

    Incident incident;

    List<MsuIncidentTask> tasksList;

    List<HistoricActivityInstance> historicActivityInstanceList;

    @Before
    public void setUp() throws Exception {
        msuIncident = new MsuIncident();
        incident = new Incident();
        incident.setId(1L);
        incident.setNumber("INC001");
        incident.setRequesterName("caojie");
        incident.setRequestDescription("Disk error");
        incident.setCreatedBy("admin");
        incident.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setUpdatedAt(incident.getCreatedAt());
        msuIncident.setIncident(incident);
        msuIncident.setResult("success");

    }

    @Test
    public void testIncidentConstraints() throws Exception {
        msuIncident.setIncident(null);
        Set<ConstraintViolation<MsuIncident>> violations = validator.validate(msuIncident);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testResultConstraints() throws Exception {
        msuIncident.setResult(null);
        Set<ConstraintViolation<MsuIncident>> violations = validator.validate(msuIncident);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testTaskConstraints() throws Exception {
        msuIncident.setTasksList(null);
        Set<ConstraintViolation<MsuIncident>> violations = validator.validate(msuIncident);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(msuIncident);
        System.out.println(json);
        MsuIncident parsed = JsonSupport.parseJson(json, MsuIncident.class);
        Assert.assertEquals(msuIncident, parsed);
    }
}
