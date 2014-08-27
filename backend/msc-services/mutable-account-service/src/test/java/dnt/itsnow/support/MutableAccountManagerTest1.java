/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.support;

import dnt.itsnow.config.MutableAccountManagerConfig1;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.AccountStatus;
import dnt.itsnow.model.MsuAccount;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.MutableAccountRepository;
import dnt.itsnow.service.MutableAccountService;
import junit.framework.Assert;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>对Account Manager的测试</h1>
 * 基于 JMock 实现
 */
@ContextConfiguration(classes = MutableAccountManagerConfig1.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MutableAccountManagerTest1 {
    // 被测试对象
    @Autowired
    MutableAccountService mutableAccountService;

    // Mock 相关
    @Autowired
    Mockery mockery;

    @Autowired
    MutableAccountRepository mutableAccountRepository;

    //测试需要用到的变量
    PageRequest pageRequest;
    Account account;
    List<Account> accounts;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
        account = new MsuAccount();
        account.setName("Test Account");
        account.setStatus(AccountStatus.Rejected);
        accounts = new ArrayList<Account>(1);
        accounts.add(account);
    }

    @Test
    public void testFindAll() throws Exception {
        Expectations expectations = new Expectations(){{
            allowing(mutableAccountRepository)
                    .findAllByType(with(any(String.class)), with(any(Pageable.class)));
            will(returnValue(accounts));
            allowing(mutableAccountRepository).countByType("msu");
            will(returnValue(2L));

        }};
        mockery.checking(expectations);

        Page<Account> msus = mutableAccountService.findAll("msu", pageRequest);
        Assert.assertEquals(2, msus.getTotalElements());
        Assert.assertEquals(1, msus.getNumberOfElements());
    }

    @Test
    public void testCreate() throws Exception {
        Expectations expectations = new Expectations(){{
            allowing(mutableAccountRepository).create(with(any(Account.class)));
            allowing(mutableAccountRepository).findBySn(with(any(String.class)));
            will(returnValue(account));
        }};
        mockery.checking(expectations);

        //验证会自动生成sn
        mutableAccountService.create(account);
        Assert.assertNotNull(account.getSn());
        Assert.assertEquals(AccountStatus.New, account.getStatus());

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }
}
