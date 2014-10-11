package dnt.itsnow.support;

import dnt.itsnow.config.MscGroupManagerConfig;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.MscGroupService;
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
@ContextConfiguration(classes = MscGroupManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MscGroupManagerTest {

    PageRequest pageRequest;

    @Autowired
    MscGroupService service;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testFindAll() throws Exception {
        Page<Group> groups = service.findAll("", pageRequest);
        Assert.assertTrue(groups.getContent().size()>0);
    }

    @Test
    public void testFindByName() throws Exception {
        Assert.assertNotNull(service.findByName("administrators"));
        Assert.assertNull(service.findByName("1000000"));
    }

    @Test
    public void testCreate() throws Exception {
        Group group = new Group();
        group.setName("GROUP_SERVICE_TEST");
        group.setDescription("This is a test.");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        group.setUpdatedAt(group.getCreatedAt());
        service.create(group);
        Assert.assertNotNull(group.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        Group group = service.findByName("first_line");
        Assert.assertNotNull(group);
        service.destroy(group);
        Assert.assertNull(service.findByName(group.getName()));
    }

    @Test
    public void testUpdate() throws Exception {
        Group group = service.findByName("administrators");
        group.setDescription("Hello World!");
        service.update(group);
        group = service.findByName(group.getName());
        Assert.assertTrue(group.getDescription().equals("Hello World!"));
    }

    @Test
    public void testFindAllRelevantInfo() throws Exception {
        Page<Group> groups = service.findAllRelevantInfo("administrators", pageRequest);
        Assert.assertTrue(groups.getContent().size()>0);
    }

}
