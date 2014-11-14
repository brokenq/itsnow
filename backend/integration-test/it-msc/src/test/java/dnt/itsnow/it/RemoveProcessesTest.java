package dnt.itsnow.it;

import dnt.itsnow.model.ClientProcessStatus;
import dnt.itsnow.util.ShareDatas;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Remove Process Data
 */
@Ignore
public class RemoveProcessesTest extends AbstractTest{

    private ItsnowProcessesTest processesTest;

    @Before
    public void setUp() throws Exception {
        processesTest = new ItsnowProcessesTest();
    }

    @Test
    public void testRemoveProcess() throws Exception {
        processesTest.stop(ShareDatas.process);
        ShareDatas.process = processesTest.show(ShareDatas.process);
        ShareDatas.process = processesTest.waitFinished(ShareDatas.process, ShareDatas.PROCESS_STOP_INVOCATION_ID);
        Assert.assertTrue(ShareDatas.process.getStatus() == ClientProcessStatus.Stopped);
        processesTest.destroy(ShareDatas.process);
        Assert.assertTrue(null == processesTest.show(ShareDatas.process));
        ShareDatas.process = null;
    }
}
