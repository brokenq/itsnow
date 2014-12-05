package dnt.itsnow.it;

import dnt.itsnow.model.ClientItsnowProcess;
import dnt.itsnow.util.ShareDatas;
import org.junit.Assert;
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
        processesTest.waitFinished(ShareDatas.process, ShareDatas.PROCESS_STOP_INVOCATION_ID);
        processesTest.destroy(ShareDatas.process);
        ClientItsnowProcess showing = null;
        try {
            showing = processesTest.show(ShareDatas.process);
        } catch (Exception e) {
        }
        Assert.assertNull(showing);
        ShareDatas.process = null;
    }
}
