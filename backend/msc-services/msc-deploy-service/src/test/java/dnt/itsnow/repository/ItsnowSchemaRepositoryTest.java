/**
 * Developer: Kadvin Date: 14-9-15 下午3:23
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.DeployRepositoryConfig;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.platform.util.PageRequest;
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
 * 测试 Itsnow Schema Repository
 */
@ContextConfiguration(classes = DeployRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ItsnowSchemaRepositoryTest {
    @Autowired
    ItsnowSchemaRepository schemaRepository;

    ItsnowSchema schema;

    @Before
    public void setUp() throws Exception {
        schema = DeployFixture.testSchema();
    }

    @Test
    public void testCreate() throws Exception {
        schemaRepository.create(schema);
        Assert.assertNotNull(schema.getId());
    }

    @Test
    public void testFindByName() throws Exception {
        ItsnowSchema found = schemaRepository.findByName("itsnow_msc");
        Assert.assertNotNull(found);
        Assert.assertNotNull(found.getConfiguration());
        Assert.assertNotNull(found.getHost());
        Assert.assertNotNull(found.getHostId());
        Assert.assertEquals("root", found.getConfiguration().getProperty("user"));
    }

    @Test
    public void testFindById() throws Exception {
        ItsnowSchema found = schemaRepository.findById(1);
        Assert.assertNotNull(found);
        Assert.assertNotNull(found.getConfiguration());
        Assert.assertNotNull(found.getHost());
        Assert.assertNotNull(found.getHostId());
        Assert.assertEquals("root", found.getConfiguration().getProperty("user"));
    }

    @Test
    public void testDelete() throws Exception {
        schema.setName("another_new_schema");
        schemaRepository.create(schema);
        schemaRepository.delete(schema);
        ItsnowSchema found = schemaRepository.findByName(schema.getName());
        Assert.assertNull(found);
    }
    @Test
    public void testCountByKeyword() throws Exception {
        int count = schemaRepository.countByKeyword("%msc%");
        Assert.assertEquals(1, count);
    }

    @Test
    public void testCountNoKeyword() throws Exception {
        int count = schemaRepository.countByKeyword(null);
        Assert.assertTrue(count >= 1);
    }

    @Test
    public void testFindAllByKeyword() throws Exception {
        //实际环境中，大小写不敏感，但H2 database没有很好的支持，所以在测试用例中不进行case insensitive测试
        List<ItsnowSchema> schemas = schemaRepository.findAllByKeyword("%MS%", new PageRequest(0, 10));
        Assert.assertEquals(1, schemas.size());
    }

    @Test
    public void testFindAllNoKeyword() throws Exception {
        List<ItsnowSchema> schemas = schemaRepository.findAllByKeyword(null, new PageRequest(0, 10));
        Assert.assertTrue(schemas.size() >= 1);
    }

    @Test
    public void testCountLinkProcesses() throws Exception {
        int count = schemaRepository.countLinkProcesses(1L);
        Assert.assertTrue(count >= 1);
    }
}
