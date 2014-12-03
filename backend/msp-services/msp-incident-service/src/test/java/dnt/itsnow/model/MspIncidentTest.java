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
public class MspIncidentTest extends ValidatorSupport{

    MspIncident mspIncident;

    Incident incident;

    List<MspIncidentTask> tasksList;

    List<HistoricActivityInstance> historicActivityInstanceList;

    @Before
    public void setUp() throws Exception {
        mspIncident = new MspIncident();
        incident = new Incident();
        incident.setId(1L);
        incident.setNumber("INC001");
        incident.setRequesterName("caojie");
        incident.setRequestDescription("Disk error");
        incident.setCreatedBy("admin");
        incident.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setUpdatedAt(incident.getCreatedAt());
        mspIncident.setIncident(incident);
        mspIncident.setResult("success");

    }

    @Test
    public void testIncidentConstraints() throws Exception {
        mspIncident.setIncident(null);
        Set<ConstraintViolation<MspIncident>> violations = validator.validate(mspIncident);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testTaskConstraints() throws Exception {
        mspIncident.setTasksList(null);
        Set<ConstraintViolation<MspIncident>> violations = validator.validate(mspIncident);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(mspIncident);
        System.out.println(json);
        MspIncident parsed = JsonSupport.parseJson(json, MspIncident.class);
        Assert.assertEquals(mspIncident, parsed);
    }
}
