package dnt.itsnow.repository;

import dnt.itsnow.model.Role;
import dnt.itsnow.model.UserAuthority;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.config.RoleRepositoryConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * <h1>测试RoleRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = RoleRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class RoleRepositoryTest {

    PageRequest pageRequest;

    @Autowired
    RoleRepository repository;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testCreate() throws Exception {
        Role role = new Role();
        role.setName("ROLE_TEST");
        role.setDescription("This is a test.");
        role.creating();
        repository.create(role);
        Assert.assertNotNull(role.getId());
    }

    @Test
    public void testDelete() throws Exception {
        Role role = repository.findByName("ROLE_GUEST");
        Assert.assertNotNull(role);
        repository.deleteRoleAndGroupRelationByRoleName("ROLE_GUEST");
        repository.delete("ROLE_GUEST");
        Assert.assertNull(repository.findByName("ROLE_GUEST"));
    }

    @Test
    public void testUpdate() throws Exception {
        Role role = repository.findByName("ROLE_ADMIN");
        role.setDescription("Hello World!");
        role.updating();
        repository.update(role);
        role = repository.findByName("ROLE_ADMIN");
        Assert.assertTrue(role.getDescription().equals("Hello World!"));
    }

    @Test
    public void testCount() throws Exception {
        Integer count = repository.count(null);
        Assert.assertSame(Integer.class, count.getClass());
    }

    @Test
    public void testFind() throws Exception {
        List<Role> roles = repository.findAll("", pageRequest);
        Assert.assertNotNull(roles);
    }

    @Test
    public void testFindByName() throws Exception {
        Assert.assertNotNull(repository.findByName("ROLE_ADMIN"));
        Assert.assertNull(repository.findByName("1000000"));
    }

    @Test
    public void testCreateRoleAndUserRelation() throws Exception {
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setUsername("USER_DAO_TEST");
        userAuthority.setAuthority("ROLE_DAO_TEST");
        repository.createRoleAndUserRelation(userAuthority);

        UserAuthority ua = repository.findRoleAndUserRelation(userAuthority);
        Assert.assertNotNull(ua);
    }

    @Test
    public void testDeleteRoleAndUserRelation() throws Exception {
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setUsername("susie.qian");
        userAuthority.setAuthority("ROLE_LINE_TWO");
        repository.deleteRoleAndUserRelation(userAuthority);

        UserAuthority ua = repository.findRoleAndUserRelation(userAuthority);
        Assert.assertNull(ua);
    }

}
