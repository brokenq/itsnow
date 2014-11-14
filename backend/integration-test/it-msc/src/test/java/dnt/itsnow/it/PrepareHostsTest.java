package dnt.itsnow.it;

import dnt.itsnow.model.ClientHostStatus;
import dnt.itsnow.model.ClientItsnowHost;
import dnt.itsnow.util.ShareDatas;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Prepare Host Data
 */
public class PrepareHostsTest extends AbstractTest{

    private ItsnowHostsTest hostsTest;

    @Before
    public void setUp() throws Exception {
        hostsTest = new ItsnowHostsTest();
    }

    @Test
    public void testCreateHost() throws Exception {
        ClientItsnowHost host = hostsTest.create();
        Assert.assertNotNull(host);
        Assert.assertTrue(host.getStatus() == ClientHostStatus.Planing);

        host = hostsTest.waitHostCreation(host);
        Assert.assertTrue(host.getStatus() == ClientHostStatus.Running);
        ShareDatas.host = host;
    }
}
