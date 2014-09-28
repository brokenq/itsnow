/**
 * Developer: Kadvin Date: 14-9-15 下午3:23
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.DeployRepositoryConfig;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.util.DeployFixture;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
