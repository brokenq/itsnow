package dnt.itsnow.it;

import dnt.itsnow.util.ShareDatas;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Remove Account Data
 */
public class RemoveAccountsTest extends AbstractTest{

    private AccountsTest accountsTest;

    @Before
    public void setUp() throws Exception {
        accountsTest = new AccountsTest();
    }

    @Test
    public void testRemoveAccount() throws Exception {
        accountsTest.destroy(ShareDatas.account);
        Assert.assertTrue(null == accountsTest.show(ShareDatas.account));
        ShareDatas.account = null;
    }
}
