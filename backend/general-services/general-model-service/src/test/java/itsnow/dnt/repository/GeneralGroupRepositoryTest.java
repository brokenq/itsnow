package itsnow.dnt.repository;

import dnt.itsnow.model.Group;
import dnt.itsnow.repository.GeneralGroupRepository;
import itsnow.dnt.config.GeneralGroupRepositoryConfig;
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
 * <h1>测试GroupRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = GeneralGroupRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class GeneralGroupRepositoryTest {

    @Autowired
    GeneralGroupRepository repository;

    @Test
    public void testCreate() throws Exception {
        Group group = new Group();
        group.setName("用户");
        group.setDescription("This is a test.");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        group.setUpdatedAt(group.getCreatedAt());

        repository.create(group);
        Assert.notNull(group.getId());
    }

    @Test
    public void testDelete() throws Exception {
        List<Group> groups = repository.findAll("updated_at", "desc", 0, 1);
        Group group = groups.get(0);
        repository.deleteGroupAuthority(group.getId());
        repository.deleteGroupMember(group.getId());
        repository.delete(group.getName());
        Assert.isNull(repository.findByName(group.getName()));
    }

    @Test
    public void testUpdate() throws Exception {
        List<Group> groups = repository.findAll("updated_at", "desc", 0, 1);
        Group group = groups.get(0);
        group.setDescription("Hello World!");
        repository.update(group);
        group = repository.findByName(group.getName());
        Assert.isTrue(group.getDescription().equals("Hello World!"));
    }

    @Test
    public void testCount() throws Exception {
        Assert.notNull(repository.count());
    }

    @Test
    public void testFind() throws Exception {
        Assert.notNull(repository.findAll("updated_at", "desc",  0, 10));
    }

    @Test
    public void testCountByKeyword() throws Exception {
        Assert.notNull(repository.countByKeyword("%s%"));
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.notNull(repository.findAllByKeyword("%s%","updated_at","desc", 0, 10));
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.notNull(repository.findByName("administrators"));
        Assert.isNull(repository.findByName("1000000"));
    }

}
