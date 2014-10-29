/**
 * Developer: Kadvin Date: 14-9-18 上午10:54
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.listener.SystemInvocationListener;
import dnt.itsnow.model.SystemInvocation;
import dnt.itsnow.service.SystemInvocationTranslator;
import dnt.itsnow.service.SystemInvokeService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <h1>Default shared code between itsnow host/schema/process manager</h1>
 */
public abstract class ItsnowResourceManager extends Bean implements SystemInvocationListener {
    public static final String CREATE_INVOCATION_ID = "createInvocationId";
    public static final String DELETE_INVOCATION_ID = "deleteInvocationId";

    @Autowired
    SystemInvocationTranslator translator;
    @Autowired
    SystemInvokeService        invokeService;

    String mscAddress;

    @Override
    protected void performStart() {
        invokeService.addListener(this);
        try {
            if(mscAddress == null ) mscAddress = initMscAddress();
        } catch (UnknownHostException e) {
            throw new ApplicationContextException("Failed to resolve msc address" , e);
        }
    }

    @Override
    protected void performStop() {
        invokeService.removeListener(this);
    }

    private String initMscAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }


    @Override
    public void added(SystemInvocation invocation) {
        logger.debug("{} added", invocation.getId());
    }

    @Override
    public void started(SystemInvocation invocation) {
        logger.debug("{} started", invocation.getId());

    }

    @Override
    public void stepExecuted(SystemInvocation invocation) {
        logger.debug("{} stepExecuted:{}", invocation.getId(), invocation.getSequence());
    }

    @Override
    public void finished(SystemInvocation invocation) {
        logger.debug("{} finished", invocation.getId());

    }

    @Override
    public void cancelled(SystemInvocation invocation) {
        logger.debug("{} cancelled", invocation.getId());
    }

    @Override
    public void failed(SystemInvocation invocation, SystemInvokeException e) {
        logger.debug("{} failed, because of {}", invocation.getId(), e.getMessage());

    }
}
