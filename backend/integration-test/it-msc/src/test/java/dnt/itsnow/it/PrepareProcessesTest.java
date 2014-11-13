package dnt.itsnow.it;

import dnt.itsnow.model.ClientItsnowProcess;
import dnt.itsnow.model.ClientProcessStatus;
import dnt.itsnow.util.ShareDatas;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Prepare Process Data
 */
@Ignore
public class PrepareProcessesTest extends AbstractTest{

    private ItsnowProcessesTest processesTest;

    @Before
    public void setUp() throws Exception {
        processesTest = new ItsnowProcessesTest();
    }

    @Test
    public void testCreateProcess() throws Exception {
        ClientItsnowProcess process = processesTest.autoNew(ShareDatas.account);
        Assert.assertNotNull(process);

        process.setHostId(ShareDatas.host.getId());
        process = processesTest.create(process);
        Assert.assertNotNull(process);

        processesTest.start(process);
        process = processesTest.waitStarted(process);
        Assert.assertTrue(process.getStatus() == ClientProcessStatus.Running);
        ShareDatas.process = process;
    }
}
