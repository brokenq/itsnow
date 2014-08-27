/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.MutableAccountRepositoryConfig;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.AccountStatus;
import dnt.itsnow.model.MsuAccount;
import dnt.itsnow.platform.util.PageRequest;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * <h1>测试MutableAccountRepository的Mybatis的Mapping配置是否正确</h1>
 *
 * 备注：
 * <ul>
 * <li>当前基本只进行 Happy Case 测试
 * <li>非Happy Case，应该在开发过程中，遇到一个问题/坑，就增加一个相应的测试用例
 * </ul>
 */
@ContextConfiguration(classes = MutableAccountRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MutableAccountRepositoryTest {
    @Autowired
    CommonAccountRepository repository;
    @Autowired
    MutableAccountRepository mutableRepository;
    PageRequest pageRequest;
    Account account;

    String newAccountSn = "msu_100";

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 2);
        account = new MsuAccount();
        account.setName("Test Account");
        account.setDomain("test");
        account.setSn(newAccountSn);
        account.setStatus(AccountStatus.New);
    }

    @Test
    public void testCountByType() throws Exception {
        Assert.assertEquals(mutableRepository.countByType("msc"), 1);
        Assert.assertEquals(mutableRepository.countByType("msu"), 2);
        Assert.assertEquals(mutableRepository.countByType("msp"), 2);
    }

    @Test
    public void testFindAllByType() throws Exception {
        List<Account> mscs = mutableRepository.findAllByType("msc", pageRequest);
        Assert.assertEquals(1, mscs.size());
        List<Account> msus = mutableRepository.findAllByType("msu", pageRequest);
        Assert.assertEquals(2, msus.size());
        List<Account> msps = mutableRepository.findAllByType("msp", pageRequest);
        Assert.assertEquals(2, msps.size());
    }

    @Test
    public void testCreate() throws Exception {
        try {
            Account created = prepareNewAccount();
            // 不比较 createdAt, updatedAt
            Assert.assertEquals(account, created);
        } finally {
            clearNewAccount();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        try {
            prepareNewAccount();

            // 现在 update 只更新name, domain
            account.setName("New Name");
            account.setDomain("another");

            mutableRepository.update(account);
            Account updated = repository.findBySn(account.getSn());
            Assert.assertEquals(account, updated);


            String sn = account.getSn();
            account.setSn("msu_010");
            // no account with sn = updated sn
            updated = repository.findBySn(account.getSn());
            Assert.assertNull(updated);

            // account can be found by old sn, and not equals
            updated = repository.findBySn(sn);
            Assert.assertNotNull(updated);
            Assert.assertFalse(account.equals(updated));

        } finally {
            clearNewAccount();
        }
    }

    @Test
    public void testDeleteBySn() throws Exception {
        try {
            // 先生成
            prepareNewAccount();
            mutableRepository.deleteBySn(account.getSn());

            Account deleted = repository.findBySn(account.getSn());
            Assert.assertNull(deleted);
        } finally {
            clearNewAccount();
        }
    }

    Account prepareNewAccount() {
        mutableRepository.create(account);
        Account created = repository.findBySn(account.getSn());
        // for assert
        account.setId(created.getId());
        return created;
    }

    //清除生成的数据，避免因为这些用例先跑导致前面的count被安排后跑导致的失败
    void clearNewAccount(){
        mutableRepository.deleteBySn(newAccountSn);

    }

}
