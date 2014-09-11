/**
 * xiongjie on 14-8-6.
 */
package itsnow.dnt.repository;

import dnt.itsnow.model.WorkTime;
import dnt.itsnow.repository.WorkTimeRepository;
import itsnow.dnt.config.WorkTimeRepositoryConfig;
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

    @Test
    public void testCreate() throws Exception {
        WorkTime workTime = new WorkTime();
        workTime.setSn("plan7");
        workTime.setName("工作日计划七");
        workTime.setWorkDays("1,2,4,5,6");
        workTime.setStartedAt("9:00");
        workTime.setEndedAt("17:30");
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
        Assert.notNull(repository.count());
    }

    @Test
    public void testFind() throws Exception {
        Assert.notNull(repository.find("updated_at", "desc",  0, 10));
    }

    @Test
    public void testCountByKeyword() throws Exception {
        Assert.notNull(repository.countByKeyword("%工作日%"));
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.notNull(repository.findByKeyword("%工作日%","updated_at","desc", 0, 10));
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.notNull(repository.findBySn("plan2"));
        Assert.isNull(repository.findBySn("plan000"));
    }

}
