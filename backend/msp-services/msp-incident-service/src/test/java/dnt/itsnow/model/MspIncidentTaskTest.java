/**
 * xiongjie on 14-8-7.
 */
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import net.happyonroad.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * <h1>MSP故障任务模型的测试</h1>
 *
 * 这种模型测试主要包括两大方面：
 * <ul>
 * <li>模型完整性</li>
 * <li>JSON序列化</li>
 * <li>业务方法</li>
 * </ul>Ms
 */
public class MspIncidentTaskTest extends ValidatorSupport{

    MspIncidentTask mspIncidentTask;

    @Before
    public void setUp() throws Exception {
        mspIncidentTask = new MspIncidentTask();
        mspIncidentTask.setTaskId("1");
        mspIncidentTask.setTaskName("test");
        mspIncidentTask.setTaskDescription("desc");
        mspIncidentTask.setTaskAssignee("");

    }

    @Test
    public void testAssigneeConstraints() throws Exception {
        mspIncidentTask.setTaskAssignee(null);
        Set<ConstraintViolation<MspIncidentTask>> violations = validator.validate(mspIncidentTask);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(mspIncidentTask);
        System.out.println(json);
        MspIncidentTask parsed = JsonSupport.parseJson(json, MspIncidentTask.class);
        Assert.assertEquals(mspIncidentTask, parsed);
    }
}
