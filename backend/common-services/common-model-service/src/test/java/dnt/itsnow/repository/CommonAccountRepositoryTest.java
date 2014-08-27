/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.CommonAccountRepositoryConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * <h1>测试CommonAccountRepository的Mybatis的Mapping配置是否正确</h1>
 *
 * TODO 需要测试 Account + User的方式
 */
@ContextConfiguration(classes = CommonAccountRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonAccountRepositoryTest {
    @Autowired
    CommonAccountRepository repository;

    @Test
    public void testFindByName() throws Exception {
        Assert.notNull(repository.findByName("Itsnow Carrier"));
        Assert.isNull(repository.findByName("Not Exist"));
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.notNull(repository.findBySn("msc"));
        Assert.isNull(repository.findBySn("bad"));
    }

    @Test
    public void testFindById() throws Exception {
        Long mscId = repository.findBySn("msc").getId();
        Assert.notNull(repository.findById(mscId));
        Assert.isNull(repository.findById(100L));
    }
}
