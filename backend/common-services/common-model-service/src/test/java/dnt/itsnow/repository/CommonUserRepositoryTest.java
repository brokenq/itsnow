/**
 * Developer: Kadvin Date: 14-9-16 下午4:31
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.CommonAccountRepositoryConfig;
import dnt.itsnow.model.MscAccount;
import dnt.itsnow.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Common User Repository Test
 */
@ContextConfiguration(classes = CommonAccountRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonUserRepositoryTest {
    @Autowired
    CommonUserRepository repository;

    @Test
    public void testFindByUsername() throws Exception {
        User user = repository.findByFieldAndValue("username", "admin");
        validateUser(user);
        Assert.isNull(repository.findByFieldAndValue("username", "Not Exist"));
    }

    @Test
    public void testFindByEmail() throws Exception {
        User user = repository.findByFieldAndValue("email", "admin@itsnow.com");
        validateUser(user);
        Assert.isNull(repository.findByFieldAndValue("email", "NotExist@itsnow.com"));
    }

    @Test
    public void testFindByPhone() throws Exception {
        User user = repository.findByFieldAndValue("phone", "13012345678");
        validateUser(user);
        Assert.isNull(repository.findByFieldAndValue("phone", "123456678"));
    }

    @Test
    public void testFindByAccountSnAndUsername() throws Exception {
        User user = repository.findByAccountSnAndUsername("msc", "admin");
        validateUser(user);
    }


    @Test
    public void testFindByIncorrectAccountSnAndCorrectUsername() throws Exception {
        User user = repository.findByAccountSnAndUsername("msu_001", "admin");
        Assert.isNull(user);
    }

    @Test
    public void testFindAuthorities() throws Exception {
        // TODO
    }

    protected void validateUser(User user) {
        Assert.notNull(user);
        Assert.notNull(user.getAccountId());
        Assert.notNull(user.getAccount());
        Assert.isTrue(user.getAccount() instanceof MscAccount);
    }
}
