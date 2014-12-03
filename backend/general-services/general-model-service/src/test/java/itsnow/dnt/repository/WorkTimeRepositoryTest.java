/**
 * xiongjie on 14-8-6.
 */
package itsnow.dnt.repository;

import dnt.itsnow.model.WorkTime;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.repository.WorkTimeRepository;
import itsnow.dnt.config.WorkTimeRepositoryConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.sql.Timestamp;

/**
 * <h1>测试WorkTimeRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = WorkTimeRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class WorkTimeRepositoryTest {

    @Autowired
    WorkTimeRepository repository;

    PageRequest pageRequest;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testCreate() throws Exception {
        WorkTime workTime = new WorkTime();
        workTime.setSn("plan7");
        workTime.setName("工作日计划七");
        workTime.setWorkDays("1,2,4,5,6");
        workTime.setStartAt("9:00");
        workTime.setEndAt("17:30");
        workTime.setDescription("It's test.");
        workTime.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        workTime.setUpdatedAt(workTime.getCreatedAt());
        repository.create(workTime);
        Assert.notNull(workTime.getId());
    }

    @Test
    public void testDelete() throws Exception {
        String sn = "plan6";
        Assert.notNull(repository.findBySn(sn));
        repository.delete(sn);
        Assert.isNull(repository.findBySn(sn));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "plan5";
        WorkTime workTime = repository.findBySn(sn);
        workTime.setSn("plan8");
        repository.update(workTime);
        workTime = repository.findBySn("plan8");
        Assert.notNull(workTime);
    }

    @Test
    public void testCount() throws Exception {
        Assert.notNull(repository.count(""));
    }

    @Test
    public void testFindAll() throws Exception {
        Assert.notNull(repository.findAll("pageRequest", pageRequest));
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.notNull(repository.findBySn("plan2"));
        Assert.isNull(repository.findBySn("no exit of sn"));
    }

}
