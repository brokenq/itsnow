/**
 * Developer: Kadvin Date: 14-9-23 上午8:19
 */
package dnt.itsnow.support;

import dnt.itsnow.config.SystemInvokeConfig;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.LocalInvocation;
import dnt.itsnow.model.RemoteInvocation;
import dnt.itsnow.model.SystemInvocation;
import dnt.itsnow.system.*;
import dnt.itsnow.system.Process;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Test Default System Invoker
 */
@ContextConfiguration(classes = SystemInvokeConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore("Need linux/osx, trusted by srv2.itsnow.com")
public class DefaultSystemInvokerTest extends AbstractProcessTest {

    @Autowired
    DefaultSystemInvoker systemInvoker;

    @Test
    public void testInvokeTwoOk() throws Exception {
        SystemInvocation invocation = new LocalInvocation() {
            @Override
            public int perform(dnt.itsnow.system.Process process) throws Exception {
                return process.run("./test.sh", "hello", "world");
            }
        }.next(new RemoteInvocation(remoteHost, remoteDir) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./test.sh", "hi", "itsnow!");
            }
        });
        invocation.setId("two-ok");
        systemInvoker.invoke(invocation);

        List<String> lines = invocation.getOutputs();
        Assert.assertEquals("hello world", lines.get(0));
        Assert.assertEquals("hi itsnow!", lines.get(1));
    }

    @Test
    public void testInvokeOkAndBad() throws Exception {
        SystemInvocation invocation = new LocalInvocation() {
            @Override
            public int perform(dnt.itsnow.system.Process process) throws Exception {
                return process.run("./test.sh", "hello", "world");
            }
        }.next(new RemoteInvocation(remoteHost, remoteDir) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./bad.sh", "bad", "boy");
            }
        });
        invocation.setId("ok-and-bad");
        try {
            systemInvoker.invoke(invocation);
            Assert.fail("it should throw exception");
        } catch (SystemInvokeException e) {
            System.out.println(e.getMessage());
        }

        List<String> lines = invocation.getOutputs();
        Assert.assertTrue(lines.size() >= 2);
        Assert.assertEquals("hello world", lines.get(0));
        Assert.assertTrue(lines.get(1).contains("No such file or directory"));
    }

    @Test
    public void testInvokeBadAndOk() throws Exception {
        SystemInvocation invocation = new LocalInvocation() {
            @Override
            public int perform(dnt.itsnow.system.Process process) throws Exception {
                return process.run("./bad.sh", "bad", "girl");
            }
        }.next(new RemoteInvocation(remoteHost, remoteDir) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./test.sh", "hi", "itsnow!");
            }
        });
        invocation.setId("bad-and-ok");
        try {
            systemInvoker.invoke(invocation);
            Assert.fail("it should throw exception");
        } catch (SystemInvokeException e) {
            System.out.println(e.getMessage());
        }

        Assert.assertTrue(invocation.getOutput().contains("No such file or directory"));

        // 验证第二个根本没有执行机会
        Assert.assertNull(invocation.getNext().getProcess());
    }

    @Test
    public void testInvokeTwoBad() throws Exception {
        SystemInvocation invocation = new LocalInvocation() {
            @Override
            public int perform(dnt.itsnow.system.Process process) throws Exception {
                return process.run("./bad.sh", "bad", "girl");
            }
        }.next(new RemoteInvocation(remoteHost, remoteDir) {
            @Override
            public int perform(Process process) throws Exception {
                return process.run("./shit.sh", "hi", "itsnow!");
            }
        });
        invocation.setId("two-bad");
        try {
            systemInvoker.invoke(invocation);
            Assert.fail("it should throw exception");
        } catch (SystemInvokeException e) {
            System.out.println(e.getMessage());
        }

        Assert.assertTrue(invocation.getOutput().contains("No such file or directory"));
        // 验证第二个根本没有执行机会
        Assert.assertNull(invocation.getNext().getProcess());
    }
}
