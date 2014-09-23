/**
 * Developer: Kadvin Date: 14-9-22 下午3:58
 */
package dnt.itsnow.system;

import dnt.itsnow.model.RemoteInvocation;
import dnt.util.StringUtils;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

/**
 * <h1>测试对远程命令的调用</h1>
 */
@Ignore("the ci or other developer's host isn't trusted by srv2")
public class RemoteProcessTest extends AbstractProcessTest {

    //we need setup authorized relationship
    @Before
    public void setUpScript() throws Exception {
        File testSh = new File(System.getProperty("APP_HOME"), "script/msc/test.sh");
        String[] sshCommands = new String[]{"ssh", "root@" + remoteHost, "mkdir -p "+ remoteDir + ""};
        String[] scpCommands = new String[]{"scp", testSh.getAbsolutePath(), "root@"+ remoteHost + ":" + remoteDir};
        java.lang.Process mkdir = Runtime.getRuntime().exec(sshCommands);
        if( mkdir.waitFor() != 0 )
            throw new Exception("Can't execute: " + StringUtils.join(sshCommands, " ") + ", exit code: " + mkdir.exitValue());
        java.lang.Process scp = Runtime.getRuntime().exec(scpCommands);
        if( scp.waitFor() != 0 )
            throw new Exception("Can't execute: " + StringUtils.join(scpCommands, " ") + ", exit code: " + mkdir.exitValue());
    }

    @Test
    public void testInvokeRemoteCommand() throws Exception {
        RemoteInvocation invocation = new RemoteInvocation(remoteHost, remoteDir) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./test.sh", "hi", "itsnow");
            }
        };
        invocation.setId("remote-invocation");
        RemoteProcess process = new RemoteProcess(invocation, executorService);
        int exitCode = invocation.perform(process);
        Assert.assertEquals(0, exitCode);
        Assert.assertEquals("hi itsnow", process.getOutput());
        Assert.assertEquals("", process.getError());
    }
}
