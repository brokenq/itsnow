/**
 * Developer: Kadvin Date: 14-9-21 下午4:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.listener.InvocationEventBroadcaster;
import dnt.itsnow.listener.ListenerNotifier;
import dnt.itsnow.listener.SystemInvocationListener;
import dnt.itsnow.model.LocalInvocation;
import dnt.itsnow.model.RemoteInvocation;
import dnt.itsnow.model.SystemInvocation;
import dnt.itsnow.service.SystemInvoker;
import dnt.itsnow.system.LocalProcess;
import dnt.itsnow.system.Process;
import dnt.itsnow.system.RemoteProcess;
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

    @Autowired
    private InvocationEventBroadcaster broadcaster;

    @Override
    public int invoke(final SystemInvocation invocation) throws SystemInvokeException {
        Process process = createProcess(invocation);
        invocation.bind(process);
        int result;
        try {
            result = invocation.perform(process);
            broadcaster.broadcast(new ListenerNotifier() {
                @Override
                public void notify(SystemInvocationListener listener) {
                    if(listener.care(invocation)) listener.stepExecuted(invocation);
                }

            });
        } catch (Exception e) {
            //避免在多个链式时对异常重复包装，也许需要修改该异常的 invocation chain 描述
            // (或者不用，自身其实已经表达了, 但我现在没将其做成双向链)
            if( e instanceof  SystemInvokeException ) throw (SystemInvokeException )e;
            String message = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
            throw new SystemInvokeException("Can't invoke " + invocation + ", because of:" + message, e);
        }
        if (result == 0) {
            if (invocation.getNext() != null) {
                return invoke(invocation.getNext());
            }
            return result;
        } else {
            String suffix = invocation.getOutput();
            throw new SystemInvokeException("Exit code " + result + " while invoke: " + invocation + " " + suffix);
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
