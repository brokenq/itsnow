package dnt.itsnow.it;

import dnt.itsnow.util.ShareDatas;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Remove Process Data
 */
public class RemoveProcessesTest extends AbstractTest{

    private ItsnowProcessesTest processesTest;

    @Before
    public void setUp() throws Exception {
        processesTest = new ItsnowProcessesTest();
    }

    @Test
    public void testRemoveProcess() throws Exception {
        processesTest.stop(ShareDatas.process);
        processesTest.destroy(ShareDatas.process);
        Assert.assertTrue(null == processesTest.show(ShareDatas.process));
        ShareDatas.process = null;
    }
}
