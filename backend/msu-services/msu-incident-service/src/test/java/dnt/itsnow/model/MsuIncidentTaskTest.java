/**
 * xiongjie on 14-8-7.
 */
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import net.happyonroad.support.JsonSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * <h1>MSU故障任务模型的测试</h1>
 *
 * 这种模型测试主要包括两大方面：
 * <ul>
 * <li>模型完整性</li>
 * <li>JSON序列化</li>
 * <li>业务方法</li>
 * </ul>
 */
public class MsuIncidentTaskTest extends ValidatorSupport{

    MsuIncidentTask msuIncidentTask;

    @Before
    public void setUp() throws Exception {
        msuIncidentTask = new MsuIncidentTask();
        msuIncidentTask.setTaskId("1");
        msuIncidentTask.setTaskName("test");
        msuIncidentTask.setTaskDescription("desc");
        msuIncidentTask.setTaskAssignee("");

    }

    @Test
    public void testAssigneeConstraints() throws Exception {
        msuIncidentTask.setTaskAssignee(null);
        Set<ConstraintViolation<MsuIncidentTask>> violations = validator.validate(msuIncidentTask);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(msuIncidentTask);
        System.out.println(json);
        MsuIncidentTask parsed = JsonSupport.parseJson(json, MsuIncidentTask.class);
        Assert.assertEquals(msuIncidentTask, parsed);
    }
}
