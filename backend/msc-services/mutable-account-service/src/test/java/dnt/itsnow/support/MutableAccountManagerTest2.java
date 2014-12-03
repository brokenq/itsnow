/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.support;

import dnt.itsnow.config.MutableAccountManagerConfig2;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.AccountStatus;
import dnt.itsnow.model.MsuAccount;
import dnt.itsnow.model.User;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.service.MutableAccountService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <h1>对Account Manager的测试</h1>
 * 基于底层真实的Repository实现
 */
@ContextConfiguration(classes = MutableAccountManagerConfig2.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MutableAccountManagerTest2 {
    @Autowired
    MutableAccountService mutableAccountService;
    PageRequest pageRequest;
    Account account;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
        account = new MsuAccount();
        account.setName("Test Account");
        account.setDomain("test");
        account.setStatus(AccountStatus.New);
    }

    @Test
    public void testFindAll() throws Exception {
        Page<Account> msus = mutableAccountService.findAll("msu", pageRequest);
        Assert.assertEquals(2, msus.getTotalElements());
        Assert.assertEquals(1, msus.getNumberOfElements());
    }

    @Test
    public void testCreate() throws Exception {
        //验证会自动生成sn
        mutableAccountService.create(account);
        Assert.assertNotNull(account.getSn());
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {
        //mutableAccountService.delete(account);
    }


    @Test
    public void testRegister() throws Exception {
        User user = new User();
        user.setName("test");
        user.setEmail("test@itsnow.com");
        user.setPassword("123456");
        Account registeredAccount = mutableAccountService.register(account, user);

        Assert.assertEquals(registeredAccount.getUser(), user);
        Assert.assertEquals(user.getAccount(), registeredAccount);
    }

}
