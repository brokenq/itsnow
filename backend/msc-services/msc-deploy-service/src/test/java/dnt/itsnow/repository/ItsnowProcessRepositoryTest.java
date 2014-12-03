/**
 * Developer: Kadvin Date: 14-9-15 下午3:23
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.DeployRepositoryConfig;
import dnt.itsnow.model.ItsnowProcess;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.util.DeployFixture;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 测试 Itsnow Process Repository
 */
@ContextConfiguration(classes = DeployRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ItsnowProcessRepositoryTest {
    @Autowired
    ItsnowProcessRepository repository;
    ItsnowProcess process;

    @Before
    public void setUp() throws Exception {
        process = DeployFixture.testProcess();
    }

    @Test
    public void testCreate() throws Exception {
        repository.create(process);
        Assert.assertNotNull(process.getId());
    }

    @Test
    public void testFindByName() throws Exception {
        ItsnowProcess found = repository.findByName("itsnow-msc");
        Assert.assertNotNull(found);
        Assert.assertNotNull(found.getConfiguration());
        Assert.assertEquals("8400", found.getConfiguration().getProperty("http.port"));
        Assert.assertNotNull(found.getHostId());
        Assert.assertNotNull(found.getHost());
        Assert.assertNotNull(found.getSchemaId());
        Assert.assertNotNull(found.getSchema());
        Assert.assertNotNull(found.getAccountId());
        Assert.assertNotNull(found.getAccount());
        Assert.assertNotNull(found.getAccount().getUserId());
        // 如果auto mapping 属性没设置，这些基本值也获取不到
        Assert.assertNotNull(found.getName());
        Assert.assertNotNull(found.getWd());
    }

    @Test
    public void testUpdate() throws Exception {
        ItsnowProcess found = repository.findByName("itsnow-msc");
        found.setPid(100);
        found.setWd("/opt/itsnow/itsnow_msc");
        repository.update(found);

        ItsnowProcess updated = repository.findByName("itsnow-msc");
        Assert.assertEquals(found.getWd(), updated.getWd());
        Assert.assertEquals(found.getPid(), updated.getPid());
    }

    @Test
    public void testDeleteByName() throws Exception {
        process.setName("itsnow-msu_be_deleted");
        repository.create(process);
        repository.deleteByName(process.getName());
        Assert.assertNull(repository.findByName(process.getName()));
    }

    @Test
    public void testCountByKeyword() throws Exception {
        int count = repository.countByKeyword("%itsnow%");
        Assert.assertTrue(count >= 1);
    }

    @Test
    public void testCountWithoutKeyword() throws Exception {
        int count = repository.countByKeyword(null);
        Assert.assertTrue(count >= 1);
    }

    @Test
    public void testFindAllByKeyword() throws Exception {
        List<ItsnowProcess> processes = repository.findAllByKeyword("%itsnow%", new PageRequest(0, 10));
        Assert.assertTrue(processes.size() >= 1 );
        // 批量查询出来的Process，暂时不需要增加关联对象
        ItsnowProcess found = processes.get(0);
        Assert.assertNotNull(found.getHostId());
        Assert.assertNotNull(found.getHost());
        Assert.assertNotNull(found.getSchemaId());
        Assert.assertNotNull(found.getSchema());
        Assert.assertNotNull(found.getAccountId());
        Assert.assertNotNull(found.getAccount());
    }

    @Test
    public void testFindAllWithoutKeyword() throws Exception {
        List<ItsnowProcess> processes = repository.findAllByKeyword(null, new PageRequest(0, 10));
        Assert.assertTrue(processes.size() >= 1 );
        // 批量查询出来的Process，暂时不需要增加关联对象
        ItsnowProcess found = processes.get(0);
        Assert.assertNotNull(found.getHostId());
        Assert.assertNotNull(found.getHost());
        Assert.assertNotNull(found.getSchemaId());
        Assert.assertNotNull(found.getSchema());
        Assert.assertNotNull(found.getAccountId());
        Assert.assertNotNull(found.getAccount());
    }

    @Test
    public void testFindByConfiguration() throws Exception {
        ItsnowProcess process = repository.findByConfiguration("http.port", "8400");
        Assert.assertNotNull(process);

    }
}
