package dnt.itsnow.repository;

import dnt.itsnow.config.RoleRepositoryConfig;
import dnt.itsnow.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

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
        role.setSn("008");
        role.setName("用户");
        role.setDescription("This is a test.");
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
        repository.create(role);
        Assert.notNull(role.getId());
    }

    @Test
    public void testDelete() throws Exception {
        List<Role> roles = repository.find("updated_at", "desc", 0, 1);
        Role role = roles.get(0);
        repository.delete(role.getSn());
        Assert.isNull(repository.findBySn(role.getSn()));
    }

    @Test
    public void testUpdate() throws Exception {
        List<Role> roles = repository.find("updated_at", "desc", 0, 1);
        Role role = roles.get(0);
        role.setDescription("Hello World!");
        repository.update(role);
        role = repository.findBySn(role.getSn());
        Assert.isTrue(role.getDescription().equals("Hello World!"));
    }

    @Test
    public void testCount() throws Exception {
        Assert.notNull(repository.count());
    }

    @Test
    public void testFind() throws Exception {
        Assert.notNull(repository.find("updated_at", "desc",  0, 10));
    }

    @Test
    public void testCountByKeyword() throws Exception {
        Assert.notNull(repository.countByKeyword("%001%"));
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.notNull(repository.findByKeyword("%001%","updated_at","desc", 0, 10));
    }

    @Test
    public void testFindBySn() throws Exception {
        List<Role> roles = repository.find("updated_at", "desc", 0, 1);
        Role role = roles.get(0);
        Assert.notNull(repository.findBySn(role.getSn()));
        Assert.isNull(repository.findBySn("1000000"));
    }

}
