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
        Page<Group> page = service.findAll("", pageRequest);
        Assert.assertTrue(page.getContent().size() > 0);
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(service.findByName("administrators"));
        Assert.assertNull(service.findByName("not-exist-record"));
    }

    @Test
    public void testCreate() throws Exception {
        Group group = new Group();
        group.setName("用户测试组");
        group.setDescription("This is a test.");
        group.creating();
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
        Group group = service.findByName("administrators");
        group.setDescription("Hello World!");
        service.update(group);
        group = service.findByName(group.getName());
        Assert.assertTrue(group.getDescription().equals("Hello World!"));
    }

}
