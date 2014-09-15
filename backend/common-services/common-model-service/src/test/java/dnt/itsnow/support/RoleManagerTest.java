package dnt.itsnow.support;

import dnt.itsnow.config.RoleManagerConfig;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.RoleService;
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
@ContextConfiguration(classes = RoleManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class RoleManagerTest {

    PageRequest pageRequest;

    @Autowired
    RoleService service;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testFindAll() throws Exception {
        Page<Role> roles = service.findAll("", pageRequest);
        Assert.assertNotNull(roles.getTotalElements());
        Assert.assertNotNull(roles.getNumberOfElements());
    }

    @Test
    public void testFindBySn() throws Exception {

        Page<Role> staffs = service.findAll("", pageRequest);
        Role role = staffs.getContent().get(0);

        Assert.assertNotNull(service.findBySn(role.getSn()));
        Assert.assertNull(service.findBySn("10000"));
    }

    @Test
    public void testCreate() throws Exception {
        Role role = new Role();
        role.setId(1L);
        role.setSn("009");
        role.setName("用户");
        role.setDescription("This is a test.");
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());

        service.create(role);
        Assert.assertNotNull(role.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        Page<Role> roles = service.findAll("", pageRequest);
        Role role = roles.getContent().get(0);
        service.destroy(role);
        Assert.assertNull(service.findBySn(role.getSn()));
    }

    @Test
    public void testUpdate() throws Exception {
        Page<Role> roles = service.findAll("", pageRequest);
        Role role = roles.getContent().get(0);
        role.setDescription("Hello World!");
        service.update(role);
        role = service.findBySn(role.getSn());
        Assert.assertTrue(role.getDescription() == "Hello World!");
    }

}
