/**
 * Developer: Kadvin Date: 14-9-15 下午3:23
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.DeployRepositoryConfig;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.util.PageRequest;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Properties;

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
        host = new ItsnowHost();
        host.setAddress("192.168.0.100");
        host.setName("srv1.itsnow.com");
        host.setCapacity(10);
        host.setDescription("A test itsnow host");
        Properties configuration = new Properties();
        configuration.setProperty("username", "root");
        configuration.setProperty("password", "root1234");
        host.setConfiguration(configuration);
    }

    @Test
    public void testCreate() throws Exception {
        hostRepository.create(host);
        // 验证已经被创建
        Assert.assertNotNull(host.getId());
    }

    @Test
    public void testFindByAddress() throws Exception {
        ItsnowHost host = hostRepository.findByAddress("172.16.3.4");
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
        ItsnowHost found = hostRepository.findById(1);
        Assert.assertNotNull(found);
    }

    @Test
    public void testCountByKeyword() throws Exception {
        int count = hostRepository.countByKeyword("%Nginx%");
        Assert.assertEquals(1, count);
    }

    @Test
    public void testCountNoKeyword() throws Exception {
        int count = hostRepository.countByKeyword(null);
        Assert.assertTrue(count >= 2);
    }

    @Test
    public void testFindAllByKeyword() throws Exception {
        //大小写不敏感
        List<ItsnowHost> msHosts = hostRepository.findAllByKeyword("%Ms%", new PageRequest(0, 10));
        Assert.assertEquals(2, msHosts.size());
    }

    @Test
    public void testFindAllNoKeyword() throws Exception {
        List<ItsnowHost> msHosts = hostRepository.findAllByKeyword(null, new PageRequest(0, 10));
        Assert.assertTrue(msHosts.size() >= 2);
    }

    @Test
    public void testFindAllDbHosts() throws Exception {
        List<ItsnowHost> msHosts = hostRepository.findAllDbHosts();
        Assert.assertTrue(msHosts.size() >= 1);
    }
}
