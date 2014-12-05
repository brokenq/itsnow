/**
 * Developer: Kadvin Date: 14/11/5 上午11:14
 */
package dnt.itsnow.support;

import dnt.itsnow.config.AutoNumberInDBConfig;
import net.happyonroad.platform.util.NumberRule;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The auto number in db test
 */
@ContextConfiguration(classes = AutoNumberInDBConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class AutoNumberInDBTest {
    @Autowired
    AutoNumberInDB service;

    @Test
    public void testConfigure() throws Exception {
        service.configure("ANOTHER", "Another_%08d@1000/10");
        NumberRule rule = service.getConfiguration("ANOTHER");
        Assert.assertEquals("Another_%08d", rule.getFormat()) ;
        Assert.assertEquals(1000, rule.getStart()) ;
        Assert.assertEquals(10, rule.getIncrement()) ;
    }

    @Test
    @Ignore("H2 Not Support Function")
    public void testNext() throws Exception {
        Assert.assertEquals("Base_001010", service.next("BASE"));
        Assert.assertEquals("Base_001020", service.next("BASE"));
        Assert.assertEquals("Base_001030", service.next("BASE"));
    }
}
