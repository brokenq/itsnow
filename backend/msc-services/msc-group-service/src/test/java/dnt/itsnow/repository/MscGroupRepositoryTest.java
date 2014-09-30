package dnt.itsnow.repository;

import dnt.itsnow.config.MscGroupRepositoryConfig;
import dnt.itsnow.model.Group;
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
 * <h1>测试GroupRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = MscGroupRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MscGroupRepositoryTest {

    @Autowired
    MscGroupRepository repository;

    @Test
    public void testCreate() throws Exception {
        Group group = new Group();
        group.setName("GROUP_REPOSITORY_TEST");
        group.setDescription("This is a test.");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        group.setUpdatedAt(group.getCreatedAt());
        repository.create(group);
        Assert.assertNotNull(group.getId());
    }

    @Test
    public void testDelete() throws Exception {
        Group group = repository.findByName("second_line");
        Assert.assertNotNull(group);
        repository.deleteGroupAndUserRelationByGroupName(group.getName());
        repository.delete(group.getName());
        Assert.assertNull(repository.findByName(group.getName()));
    }

    @Test
    public void testUpdate() throws Exception {
        Group group = repository.findByName("administrators");
        System.out.println(group.getId());
        System.out.println(group.getName());
        System.out.println(group.getDescription());
        System.out.println(group.getCreatedAt());
        System.out.println(group.getUpdatedAt());
        group.setDescription("Hello World!");
        repository.update(group);
        group = repository.findByName(group.getName());
        Assert.assertTrue(group.getDescription().equals("Hello World!"));
    }

    @Test
    public void testCount() throws Exception {
        Assert.assertTrue(repository.count() > 0);
    }

    @Test
    public void testFind() throws Exception {
        Assert.assertNotNull(repository.findAll("updated_at", "desc", 0, 10));
    }

    @Test
    public void testCountByKeyword() throws Exception {
        int count = repository.countByKeyword("%admin%");
        Assert.assertTrue(count > 0);
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.assertNotNull(repository.findAllByKeyword("%admin%", "updated_at", "desc", 0, 10));
    }

    @Test
    public void testCountByRelevantInfo() throws Exception {
        int count = repository.countByRelevantInfo("administrators");
        Assert.assertTrue(count > 0);
    }

    @Test
    public void testFindAllRelevantInfo() throws Exception {
        List<Group> roles = repository.findAllRelevantInfo("administrators", "updated_at", "desc", 0, 10);
        Assert.assertNotNull(roles);
    }

    @Test
    public void testFindByName() throws Exception {
        Assert.assertNotNull(repository.findByName("administrators"));
        Assert.assertNull(repository.findByName("1000000"));
    }

}
