package dnt.itsnow.repository;

import dnt.itsnow.config.GroupRepositoryConfig;
import dnt.itsnow.model.Group;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.util.PageRequest;
import org.junit.Before;
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

    PageRequest pageRequest;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testCreate() throws Exception {
        Group group = new Group();
        group.setName("用户");
        group.setDescription("This is a test.");
        group.creating();
        group.updating();

        repository.create(group);
        Assert.notNull(group.getId());
    }

    @Test
    public void testDelete() throws Exception {
        Group group = repository.findByName("second_line");
        repository.deleteGroupAuthority(group.getId());
        repository.deleteGroupMember(group.getId());
        repository.delete(group.getName());
        Assert.isNull(repository.findByName(group.getName()));
    }

    @Test
    public void testUpdate() throws Exception {
        Group group = repository.findByName("guests");
        group.setDescription("Hello World!");
        repository.update(group);
        group = repository.findByName(group.getName());
        Assert.isTrue("Hello World!".equals(group.getDescription()));
    }

    @Test
    public void testCount() throws Exception {
        Assert.notNull(repository.count(""));
    }

    @Test
    public void testFind() throws Exception {
        Assert.notNull(repository.findAll("", pageRequest));
    }

    @Test
    public void testFindByName() throws Exception {
        Assert.notNull(repository.findByName("guests"));
        Assert.isNull(repository.findByName("no record!"));
    }

}
