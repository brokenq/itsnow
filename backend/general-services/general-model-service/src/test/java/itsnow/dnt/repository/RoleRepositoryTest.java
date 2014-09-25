package itsnow.dnt.repository;

import dnt.itsnow.model.GeneralRole;
import dnt.itsnow.repository.RoleRepository;
import itsnow.dnt.config.RoleRepositoryConfig;
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
        GeneralRole role = new GeneralRole();
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
        GeneralRole role = repository.findByName("ROLE_GUEST");
        repository.delete(role.getName());
        Assert.isNull(repository.findByName(role.getName()));
    }

    @Test
    public void testUpdate() throws Exception {
        GeneralRole role = repository.findByName("ROLE_ADMIN");
        role.setDescription("Hello World!");
        repository.update(role);
        role = repository.findByName(role.getName());
        Assert.isTrue(role.getDescription().equals("Hello World!"));
    }

    @Test
    public void testCount() throws Exception {
        Assert.notNull(repository.count(1L));
    }

    @Test
    public void testFind() throws Exception {
        Assert.notNull(repository.findAll(1L, "updated_at", "desc",  0, 10));
    }

    @Test
    public void testCountByKeyword() throws Exception {
        Assert.notNull(repository.countByKeyword(1L, "%ROLE_MONITOR%"));
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.notNull(repository.findAllByKeyword(1L, "%ROLE_MONITOR%","updated_at","desc", 0, 10));
    }

    @Test
    public void testCountByRelevantInfo() throws Exception {
        Assert.notNull(repository.countByRelevantInfo("%ROLE_MONITOR%"));
    }

    @Test
    public void testFindAllRelevantInfo() throws Exception {
        Assert.notNull(repository.findAllRelevantInfo("%ROLE_MONITOR%","updated_at","desc", 0, 10));
    }

    @Test
    public void testFindByName() throws Exception {
        Assert.notNull(repository.findByName("ROLE_ADMIN"));
        Assert.isNull(repository.findByName("1000000"));
    }

}
