package dnt.itsnow.repository;

import dnt.itsnow.config.GroupRepositoryConfig;
import dnt.itsnow.model.Group;
import dnt.itsnow.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>测试GroupRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = GroupRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupRepositoryTest {

    @Autowired
    GroupRepository repository;

    @Test
    public void testCreate() throws Exception {
        Group group = new Group();
        group.setSn("008");
        group.setName("用户");
        group.setDescription("This is a test.");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        group.setUpdatedAt(group.getCreatedAt());

        Role role = new Role();
        role.setId(1L);
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        group.setRoles(roles);

        repository.create(group);
        Assert.notNull(group.getId());
    }

    @Test
    public void testDelete() throws Exception {
        List<Group> groups = repository.find("updated_at", "desc", 0, 1);
        Group group = groups.get(0);
        repository.delete(group.getSn());
        Assert.isNull(repository.findBySn(group.getSn()));
    }

    @Test
    public void testUpdate() throws Exception {
        List<Group> groups = repository.find("updated_at", "desc", 0, 1);
        Group group = groups.get(0);
        group.setDescription("Hello World!");
        repository.update(group);
        group = repository.findBySn(group.getSn());
        Assert.isTrue(group.getDescription().equals("Hello World!"));
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
        Assert.notNull(repository.countByKeyword("%s%"));
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.notNull(repository.findByKeyword("%s%","updated_at","desc", 0, 10));
    }

    @Test
    public void testFindBySn() throws Exception {
        List<Group> groups = repository.find("updated_at", "desc", 0, 1);
        Group group = groups.get(0);
        Assert.notNull(repository.findBySn(group.getSn()));
        Assert.isNull(repository.findBySn("1000000"));
    }

}
