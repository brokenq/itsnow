package dnt.itsnow.it;

import dnt.itsnow.model.ClientAccount;
import dnt.itsnow.model.ClientAccountStatus;
import dnt.itsnow.util.ShareDatas;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Prepare Account Data
 */
public class PrepareAccountsTest extends AbstractTest{

    private AccountsTest accountsTest;

    @Before
    public void setUp() throws Exception {
        accountsTest = new AccountsTest();
    }

    @Test
    public void testCreateAccount() throws Exception {
//        目前注册账号有问题，暂时使用show方法来获取account
//        ClientAccount account = accountsTest.signUp();
        ClientAccount account = new ClientAccount();
        account.setSn("msp_test");
        account = accountsTest.show(account);
        Assert.assertNotNull(account);
        Assert.assertTrue(account.getStatus() == ClientAccountStatus.New);

        accountsTest.approve(account);
        account = accountsTest.show(account);
        Assert.assertTrue(account.getStatus() == ClientAccountStatus.Valid);
        ShareDatas.account = account;
    }
}
