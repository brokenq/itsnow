/**
 * Developer: Kadvin Date: 14-9-21 下午4:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.LocalInvocation;
import dnt.itsnow.model.RemoteInvocation;
import dnt.itsnow.model.SystemInvocation;
import dnt.itsnow.service.SystemInvoker;
import dnt.itsnow.system.*;
import dnt.itsnow.system.Process;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * <h1>The default system invoker</h1>
 */
@Component
public class DefaultSystemInvoker extends Bean implements SystemInvoker {

    @Autowired
    @Qualifier("systemInvokeExecutor")
    ExecutorService systemInvokeExecutor;


    @Override
    public void invoke(SystemInvocation invocation) throws SystemInvokeException {
        Process process = createProcess(invocation);
        int result;
        try {
            result = invocation.perform(process);
        } catch (Exception e) {
            throw new SystemInvokeException("Can't invoke " + invocation, e);
        }
        if (result == 0) {
            if (invocation.getNext() != null) {
                invoke(invocation.getNext());
            }
        } else {
            throw new SystemInvokeException("Got exit code " + result + " while invoke: " + invocation
                                            + ", reason is: " + process.getError());
        }
    }

    Process createProcess(SystemInvocation invocation) {
        if (invocation instanceof LocalInvocation) {
            return createProcess((LocalInvocation)invocation);
        }
        return createProcess((RemoteInvocation)invocation);
    }

    LocalProcess createProcess(LocalInvocation invocation) {
        return new LocalProcess(invocation, systemInvokeExecutor);
    }

    RemoteProcess createProcess(RemoteInvocation invocation) {
        return new RemoteProcess(invocation, systemInvokeExecutor);
    }



}
