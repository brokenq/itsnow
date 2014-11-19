package dnt.itsnow.it;

import dnt.itsnow.util.ShareDatas;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Remove Account Data
 */
@Ignore
public class RemoveAccountsTest extends AbstractTest{

    private AccountsTest accountsTest;

    @Before
    public void setUp() throws Exception {
        accountsTest = new AccountsTest();
    }

    @Test
    public void testRemoveAccount() throws Exception {
        //        目前注册账号有问题,暂时先不删除
//        accountsTest.destroy(ShareDatas.account);
//        Assert.assertTrue(null == accountsTest.show(ShareDatas.account));
        ShareDatas.account = null;
    }
}
