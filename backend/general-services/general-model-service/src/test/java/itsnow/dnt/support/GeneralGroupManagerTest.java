package itsnow.dnt.support;

import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.GeneralGroupService;
import itsnow.dnt.config.GeneralGroupManagerConfig;
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
@ContextConfiguration(classes = GeneralGroupManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class GeneralGroupManagerTest {

    PageRequest pageRequest;

    @Autowired
    GeneralGroupService service;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testFindAll() throws Exception {
        Page<Group> groups = service.findAll("", pageRequest);
        Assert.assertNotNull(groups.getTotalElements());
        Assert.assertNotNull(groups.getNumberOfElements());
    }

    @Test
    public void testFindBySn() throws Exception {

        Page<Group> staffs = service.findAll("", pageRequest);
        Group group = staffs.getContent().get(0);

        Assert.assertNotNull(service.findByName(group.getName()));
        Assert.assertNull(service.findByName("10000"));
    }

    @Test
    public void testCreate() throws Exception {
        Group group = new Group();
        group.setId(1L);
        group.setName("用户");
        group.setDescription("This is a test.");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        group.setUpdatedAt(group.getCreatedAt());

        service.create(group);
        Assert.assertNotNull(group.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        Group group = service.findByName("first_line");
        service.destroy(group);
        Assert.assertNull(service.findByName(group.getName()));
    }

    @Test
    public void testUpdate() throws Exception {
        Page<Group> groups = service.findAll("", pageRequest);
        Group group = groups.getContent().get(0);
        group.setDescription("Hello World!");
        service.update(group);
        group = service.findByName(group.getName());
        Assert.assertTrue(group.getDescription() == "Hello World!");
    }

}
