package dnt.itsnow.support;

import dnt.itsnow.config.MscRoleManagerConfig;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.MscRoleService;
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
@ContextConfiguration(classes = MscRoleManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MscRoleManagerTest {

    PageRequest pageRequest;

    @Autowired
    MscRoleService service;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testFindAll() throws Exception {
        Page<Role> roles = service.findAll("", pageRequest);
        Assert.assertTrue(roles.getContent().size()>0);
    }

    @Test
    public void testFindAllRelevantInfo() throws Exception {
        Page<Role> roles = service.findAllRelevantInfo("ROLE_ADMIN", pageRequest);
        Assert.assertTrue(roles.getContent().size()>0);
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
