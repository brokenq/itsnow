package dnt.itsnow.it;

import dnt.itsnow.util.ShareDatas;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Remove Host Data
 */
@Ignore
public class RemoveHostsTest extends AbstractTest{

    private ItsnowHostsTest hostsTest;

    @Before
    public void setUp() throws Exception {
        hostsTest = new ItsnowHostsTest();
    }

    @Test
    public void testRemoveHost() throws Exception {
        hostsTest.destroy(ShareDatas.host);
        Assert.assertTrue(null == hostsTest.show(ShareDatas.host));
        ShareDatas.host = null;
    }
}
