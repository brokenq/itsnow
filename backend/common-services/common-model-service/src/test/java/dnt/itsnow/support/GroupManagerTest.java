package dnt.itsnow.support;

import dnt.itsnow.config.GroupManagerConfig;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.GroupService;
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
@ContextConfiguration(classes = GroupManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupManagerTest {

    PageRequest pageRequest;

    @Autowired
    GroupService service;

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

        Assert.assertNotNull(service.findBySn(group.getSn()));
        Assert.assertNull(service.findBySn("10000"));
    }

    @Test
    public void testCreate() throws Exception {
        Group group = new Group();
        group.setId(1L);
        group.setSn("009");
        group.setName("用户");
        group.setDescription("This is a test.");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        group.setUpdatedAt(group.getCreatedAt());

        service.create(group);
        Assert.assertNotNull(group.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        Page<Group> groups = service.findAll("", pageRequest);
        Group group = groups.getContent().get(0);
        service.destroy(group);
        Assert.assertNull(service.findBySn(group.getSn()));
    }

    @Test
    public void testUpdate() throws Exception {
        Page<Group> groups = service.findAll("", pageRequest);
        Group group = groups.getContent().get(0);
        group.setDescription("Hello World!");
        service.update(group);
        group = service.findBySn(group.getSn());
        Assert.assertTrue(group.getDescription() == "Hello World!");
    }

}
