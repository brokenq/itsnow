package itsnow.dnt.repository;

import dnt.itsnow.model.Role;
import dnt.itsnow.repository.RoleRepository;
import itsnow.dnt.config.RoleRepositoryConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>测试RoleRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = RoleRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class RoleRepositoryTest {

    @Autowired
    RoleRepository repository;

    @Test
    public void testCreate() throws Exception {
        Role role = new Role();
        role.setName("ROLE_TEST");
        role.setDescription("This is a test.");
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
        repository.create(role);
        Assert.assertNotNull(role.getId());
    }

    @Test
    public void testDelete() throws Exception {
        Role role = repository.findByName("ROLE_GUEST");
        Assert.assertNotNull(role);
        repository.delete("ROLE_GUEST");
        Assert.assertNull(repository.findByName("ROLE_GUEST"));
    }

    @Test
    public void testUpdate() throws Exception {
        Role role = repository.findByName("ROLE_ADMIN");
        role.setDescription("Hello World!");
        repository.update(role);
        role = repository.findByName("ROLE_ADMIN");
        Assert.assertTrue(role.getDescription().equals("Hello World!"));
    }

    @Test
    public void testCount() throws Exception {
        int count = repository.count(1L);
        Assert.assertNotNull(count);
    }

    @Test
    public void testFind() throws Exception {
        List<Role> roles = repository.findAll(1L, "updated_at", "desc", 0, 10);
        Assert.assertNotNull(roles);
    }

    @Test
    public void testCountByKeyword() throws Exception {
        int count = repository.countByKeyword(1L, "%ROLE_ADMIN%");
        Assert.assertNotNull(count);
    }

    @Test
    public void testFindByKeyword() throws Exception {
        List<Role> roles = repository.findAllByKeyword(1L, "%ROLE_ADMIN%", "updated_at", "desc", 0, 10);
        Assert.assertNotNull(roles);
    }

    @Test
    public void testCountByRelevantInfo() throws Exception {
        int count = repository.countByRelevantInfo("ROLE_ADMIN");
        Assert.assertTrue(count>0);
    }

    @Test
    public void testFindAllRelevantInfo() throws Exception {
        List<Role> roles = repository.findAllRelevantInfo("ROLE_ADMIN", "updated_at", "desc", 0, 10);
        Assert.assertNotNull(roles);
    }

    @Test
    public void testFindByName() throws Exception {
        Assert.assertNotNull(repository.findByName("ROLE_ADMIN"));
        Assert.assertNull(repository.findByName("1000000"));
    }

}
