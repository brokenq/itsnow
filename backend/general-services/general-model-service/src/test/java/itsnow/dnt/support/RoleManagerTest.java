package itsnow.dnt.support;

import dnt.itsnow.model.Role;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.RoleService;
import itsnow.dnt.config.RoleManagerConfig;
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
 * <h1>角色管理业务测试类</h1>
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
        Page<Role> roles = service.findAll(4L, "", pageRequest);
        Assert.assertNotNull(roles.getContent());
    }

    @Test
    public void testFindAllRelevantInfo() throws Exception {
        Page<Role> roles = service.findAllRelevantInfo("ROLE_ADMIN", pageRequest);
        Assert.assertNotNull(roles.getContent());
    }

    @Test
    public void testFindByName() throws Exception {
        Assert.assertNotNull(service.findByName("ROLE_ADMIN"));
        Assert.assertNull(service.findByName("10000"));
    }

    @Test
    public void testCreate() throws Exception {
        Role role = new Role();
        role.setName("ROLE_TEST");
        role.setDescription("This is a test.");
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());

        service.create(role);
        Assert.assertNotNull(role.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        Role role = service.findByName("ROLE_REPORTER");
        Assert.assertNotNull(role);
        service.destroy(role);
        Assert.assertNull(service.findByName("ROLE_REPORTER"));
    }

    @Test
    public void testUpdate() throws Exception {
        Role role = service.findByName("ROLE_ADMIN");
        role.setDescription("Hello World!");
        service.update(role);
        role = service.findByName("ROLE_ADMIN");
        Assert.assertTrue(role.getDescription().equals("Hello World!"));
    }

}
