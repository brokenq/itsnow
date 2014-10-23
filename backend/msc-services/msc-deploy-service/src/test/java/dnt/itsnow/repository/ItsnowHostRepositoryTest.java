/**
 * Developer: Kadvin Date: 14-9-15 下午3:23
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.DeployRepositoryConfig;
import dnt.itsnow.model.HostStatus;
import dnt.itsnow.model.HostType;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.util.DeployFixture;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 测试 Itsnow Host Repository
 */
@ContextConfiguration(classes = DeployRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ItsnowHostRepositoryTest {
    @Autowired
    ItsnowHostRepository hostRepository;

    ItsnowHost host;

    @Before
    public void setUp() throws Exception {
        host = DeployFixture.testHost();
    }

    @Test
    public void testCreate() throws Exception {
        hostRepository.create(host);
        // 验证已经被创建
        Assert.assertNotNull(host.getId());
        // clean it
        hostRepository.deleteByAddress(host.getAddress());
    }

    @Test
    public void testFindByAddress() throws Exception {
        ItsnowHost host = hostRepository.findByAddress("172.16.3.4");
        Assert.assertNotNull(host);
        Assert.assertNotNull(host.getConfiguration());
        Assert.assertEquals("4x2533Mhz", host.getConfiguration().getProperty("cpu"));
    }

    @Test
    public void testFindByName() throws Exception {
        ItsnowHost host = hostRepository.findByName("MSU/P Host A");
        Assert.assertNotNull(host);
        Assert.assertNotNull(host.getConfiguration());
        Assert.assertEquals("4x2533Mhz", host.getConfiguration().getProperty("cpu"));
    }

    @Test
    public void testDeleteByAddress() throws Exception {
        host.setAddress("192.168.0.200");
        host.setName("another_new_host");
        hostRepository.create(host);
        hostRepository.deleteByAddress(host.getAddress());
        ItsnowHost found = hostRepository.findByAddress(host.getAddress());
        Assert.assertNull(found);
    }

    @Test
    public void testFindById() throws Exception {
        ItsnowHost found = hostRepository.findById(1L);
        Assert.assertNotNull(found);
    }

    @Test
    public void testCountByKeyword() throws Exception {
        int count = hostRepository.countByKeyword("%nginx%");
        Assert.assertEquals(1, count);
    }

    @Test
    public void testCountNoKeyword() throws Exception {
        int count = hostRepository.countByKeyword(null);
        Assert.assertTrue(count >= 2);
    }

    @Test
    public void testFindAllByKeyword() throws Exception {
        //实际环境中，大小写不敏感，但H2 database没有很好的支持，所以在测试用例中不进行case insensitive测试
        List<ItsnowHost> msHosts = hostRepository.findAllByKeyword("%MS%", new PageRequest(0, 10));
        Assert.assertEquals(2, msHosts.size());
    }

    @Test
    public void testFindAllByType() throws Exception {
        List<ItsnowHost> appHosts = hostRepository.findAllByType(HostType.APP);
        Assert.assertEquals(1, appHosts.size());
        List<ItsnowHost> dbHosts = hostRepository.findAllByType(HostType.DB);
        Assert.assertEquals(1, dbHosts.size());
        List<ItsnowHost> comHosts = hostRepository.findAllByType(HostType.COM);
        Assert.assertEquals(1, comHosts.size());
    }

    @Test
    public void testFindAllNoKeyword() throws Exception {
        List<ItsnowHost> msHosts = hostRepository.findAllByKeyword(null, new PageRequest(0, 10));
        Assert.assertTrue(msHosts.size() >= 2);
    }

    @Test
    public void testUpdate() throws Exception {
        ItsnowHost host = hostRepository.findByAddress("172.16.3.4");
        String originName = host.getName();
        host.setName("new name");
        host.setStatus(HostStatus.Running);
        host.updating();
        hostRepository.update(host);
        //NOT AFFECT OTHER TEST CASE
        host.setName(originName);
        hostRepository.update(host);
    }

    @Test
    public void testFindByConfiguration() throws Exception {
        ItsnowHost host = hostRepository.findByConfiguration("mem", "8g");
        Assert.assertNotNull(host);
    }

    @Test
    public void testFindAllByConfiguration() throws Exception {
        List<ItsnowHost> hosts = hostRepository.findAllByConfiguration("mem", "8g");
        Assert.assertEquals(3, hosts.size());
    }

    @Test
    @Ignore
    public void testCountLinked() throws Exception {
        int count = hostRepository.countLinked(1L);
        Assert.assertEquals(2, count);
    }
}
