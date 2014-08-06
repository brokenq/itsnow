/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.support;

import dnt.itsnow.config.CommonAccountManagerConfig;
import dnt.itsnow.service.CommonAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * <h1>Class Usage</h1>
 */
@ContextConfiguration(classes = CommonAccountManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonAccountManagerTest {
    @Autowired
    CommonAccountService service;

    @Test
    public void testFindByName() throws Exception {
        Assert.notNull(service.findByName("Itsnow Carrier"));
        Assert.isNull(service.findByName("Not Exist"));
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.notNull(service.findBySn("msc"));
        Assert.isNull(service.findBySn("bad"));
    }

    @Test
    public void testFindById() throws Exception {
        Long mscId = service.findBySn("msc").getId();
        Assert.notNull(service.findById(mscId));
        Assert.isNull(service.findById(100L));
    }
}
