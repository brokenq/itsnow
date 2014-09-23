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

        Assert.assertEquals("", invocation.getProcess().getError());
        Assert.assertEquals("hello world", invocation.getProcess().getOutput());
        Assert.assertEquals("", invocation.getNext().getProcess().getError());
        Assert.assertEquals("hi itsnow!", invocation.getNext().getProcess().getOutput());
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

        Assert.assertEquals("", invocation.getProcess().getError());
        Assert.assertEquals("hello world", invocation.getProcess().getOutput());
        // 验证第二个有执行机会，但出错
        Assert.assertNotNull(invocation.getNext().getProcess().getError());
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

        Assert.assertNotNull(invocation.getProcess().getError());

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

        Assert.assertNotNull(invocation.getProcess().getError());
        // 验证第二个根本没有执行机会
        Assert.assertNull(invocation.getNext().getProcess());
    }
}
