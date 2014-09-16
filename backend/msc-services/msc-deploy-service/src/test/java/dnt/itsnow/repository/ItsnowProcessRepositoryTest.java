/**
 * Developer: Kadvin Date: 14-9-15 下午3:23
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.DeployRepositoryConfig;
import dnt.itsnow.model.ItsnowProcess;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        process = new ItsnowProcess();
        process.setName("itsnow-msu_001");
        process.setAccountId(1L);
        process.setHostId(1L);
        process.setSchemaId(1L);
        process.setWd("/opt/releases/msu_001");
        process.setDescription("A test process");
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
        Assert.assertEquals("8071", found.getConfiguration().getProperty("http.port"));
        Assert.assertNotNull(found.getHostId());
        Assert.assertNotNull(found.getHost());
        Assert.assertNotNull(found.getSchemaId());
        Assert.assertNotNull(found.getSchema());
        Assert.assertNotNull(found.getAccountId());
        Assert.assertNotNull(found.getAccount());
        Assert.assertNotNull(found.getAccount().getUserId());
    }

    @Test
    public void testDeleteByName() throws Exception {

    }

    @Test
    public void testCountByKeyword() throws Exception {

    }

    @Test
    public void testFindAllByKeyword() throws Exception {


    }
}
