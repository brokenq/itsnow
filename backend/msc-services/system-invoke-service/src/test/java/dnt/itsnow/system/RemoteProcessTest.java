/**
 * Developer: Kadvin Date: 14-9-22 下午3:58
 */
package dnt.itsnow.system;

import dnt.itsnow.model.LocalInvocation;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.util.List;

/**
 * <h1>测试对本地命令的调用</h1>
 */
public class RemoteProcessTest extends AbstractProcessTest {

    @Test
    public void testInvokeLocalCommand() throws Exception {
        LocalInvocation invocation = new LocalInvocation() {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./test.sh", "hello", "world");
            }
        };
        invocation.setId("local-invocation");
        LocalProcess localProcess = new LocalProcess(invocation, executorService);
        invocation.perform(localProcess);
        final File stdoutFile = new File(System.getProperty("APP_HOME"), "tmp/" + invocation.getOutFileName());
        final File stderrFile = new File(System.getProperty("APP_HOME"), "tmp/" + invocation.getErrFileName());

        FileInputStream stdout = new FileInputStream(stdoutFile);
        List<String> outLines = IOUtils.readLines(stdout);
        stdout.close();
        Assert.assertEquals(1, outLines.size());
        Assert.assertEquals("hello world", outLines.get(0));

        FileInputStream stderr = new FileInputStream(stderrFile);
        List<String> errLines = IOUtils.readLines(stderr);
        stderr.close();
        Assert.assertTrue(errLines.isEmpty());
    }
}
