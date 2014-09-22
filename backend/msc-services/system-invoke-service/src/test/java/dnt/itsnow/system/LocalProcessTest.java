/**
 * Developer: Kadvin Date: 14-9-22 下午3:58
 */
package dnt.itsnow.system;

import dnt.itsnow.model.LocalInvocation;
import junit.framework.Assert;
import org.junit.Test;

/**
 * <h1>测试对本地命令的调用</h1>
 */
public class LocalProcessTest extends AbstractProcessTest {

    @Test
    public void testInvokeLocalCommand() throws Exception {
        LocalInvocation invocation = new LocalInvocation() {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./test.sh", "hello", "world");
            }
        };
        invocation.setId("local-invocation");
        LocalProcess process = new LocalProcess(invocation, executorService);
        int exitCode = invocation.perform(process);
        Assert.assertEquals(0, exitCode);
        Assert.assertEquals("hello world", process.getOutput());
        Assert.assertEquals("", process.getError());
    }
}
