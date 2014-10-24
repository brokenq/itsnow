/**
 * xiongjie on 14-8-6.
 */
package itsnow.dnt.support;

import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.WorkTimeService;
import itsnow.dnt.config.WorkTimeManagerConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

/**
 * <h1>Class Usage</h1>
 */
@ContextConfiguration(classes = WorkTimeManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class WorkTimeManagerTest {

    PageRequest pageRequest;

    @Autowired
    WorkTimeService service;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testFindAll() throws Exception {
        Page<WorkTime> dictionaries = service.findAll("", pageRequest);
        Assert.assertNotNull(dictionaries);
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(service.findBySn("plan3"));
    }

    @Test
    public void testCreate() throws Exception {
        WorkTime workTime = new WorkTime();
        workTime.setSn("plan10");
        workTime.setName("工作日计划十");
        workTime.setWorkDays("1,2,4,5,6");
        workTime.setStartAt("9:00");
        workTime.setEndAt("17:30");
        workTime.setDescription("It's test.");
        workTime.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        workTime.setUpdatedAt(workTime.getCreatedAt());
        service.create(workTime);
        Assert.assertNotNull(workTime.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        String sn = "plan4";
        Assert.assertNotNull(service.findBySn(sn));
        service.destroy(sn);
        Assert.assertNull(service.findBySn(sn));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "plan2";
        WorkTime workTime = service.findBySn(sn);
        workTime.setDescription("it's a update test");
        service.update(workTime);
        workTime = service.findBySn(sn);
        Assert.assertTrue("it's a update test".equals(workTime.getDescription()));
    }

}
