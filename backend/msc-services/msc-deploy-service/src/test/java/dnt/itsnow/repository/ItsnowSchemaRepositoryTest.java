/**
 * Developer: Kadvin Date: 14-9-15 下午3:23
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.DeployRepositoryConfig;
import dnt.itsnow.model.ItsnowSchema;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Properties;

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
        schema = new ItsnowSchema();
        schema.setName("itsnow_test");
        schema.setHostId(1L);
        schema.setDescription("The test schema");
        Properties configuration = new Properties();
        configuration.setProperty("user", "itsnow");
        configuration.setProperty("password", "secret");
        schema.setConfiguration(configuration);
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
