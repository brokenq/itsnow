package itsnow.dnt.model;

import dnt.itsnow.model.WorkTime;
import net.happyonroad.support.JsonSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;

/**
 * <h1>工作时间测试类</h1>
 */
public class WorkTimeTest {


    private WorkTime workTime;

    @Before
    public void setUp() throws Exception {
        workTime = new WorkTime();
        this.workTime.setId(1L);
        this.workTime.setSn("plan1");
        this.workTime.setName("工作日计划一");
        this.workTime.setWorkDays("1,2,3,4,5");
        this.workTime.setStartAt("9:00");
        this.workTime.setEndAt("17:30");
        this.workTime.setDescription("This is a test.");
        this.workTime.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.workTime.setUpdatedAt(this.workTime.getCreatedAt());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(workTime);
        System.out.println(json);
        Assert.assertNotNull(json);
    }

}
