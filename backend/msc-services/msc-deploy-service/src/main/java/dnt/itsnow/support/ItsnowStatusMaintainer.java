/**
 * Developer: Kadvin Date: 14-10-16 下午3:11
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.exception.ItsnowProcessException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.*;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.ItsnowHostService;
import dnt.itsnow.service.ItsnowProcessService;
import dnt.util.NamedThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 定时维护主机，进程的状态
 */
@Component
@Transactional
public class ItsnowStatusMaintainer extends ItsnowResourceManager {

    @Autowired
    ItsnowHostService    hostService;
    @Autowired
    ItsnowProcessService processService;

    ExecutorService hostExecutor, processExecutor;

    @Override
    protected void performStart() {
        super.performStart();
        hostExecutor = Executors.newSingleThreadExecutor(new NamedThreadFactory("HostStatusChecker"));
        processExecutor = Executors.newSingleThreadExecutor(new NamedThreadFactory("ProcessStatusChecker"));
        hostExecutor.submit(new Runnable() {
            @Override
            public void run() {
                while(isRunning()){
                    checkHostsStatus();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //have a rest for 1 seconds
                    }
                }

            }
        });
        processExecutor.submit(new Runnable() {
            @Override
            public void run() {
                while(isRunning()){
                    checkProcessesStatus();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //have a rest for 1 seconds
                    }
                }
            }
        });
    }

    @Override
    protected void performStop() {
        super.performStop();
        hostExecutor.shutdown();
        processExecutor.shutdown();
    }

    public void run() {
        while(isRunning()){
            checkHostsStatus();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //have a rest for 1 seconds
            }
            checkProcessesStatus();
        }
    }

    /**
     * 执行定时检测
     */
    public void checkHostsStatus(){
        List<ItsnowHost> hosts = hostService.findAllDbHosts();
        for (ItsnowHost host : hosts) {
            checkHostStatus(host);
        }
    }

    protected void checkHostStatus(ItsnowHost host) {
        // 不检测plan状态的主机
        if (host.getStatus() == HostStatus.Planing ) return;

        logger.debug("Checking status of {}({})", host, host.getStatus());

        SystemInvocation invocation = translator.check(host);
        String id = invokeService.addJob(invocation);
        int code = -1;
        try {
            code = invokeService.waitJobFinished(id);
        } catch (SystemInvokeException e) {
            logger.warn("Failed to check status of host: {}, mark it as abnormal, reason: {}", host, e);
        }
        //超时
        // 0: ok
        // 1: shutdown
        // 2: abnormal
        HostStatus oldStatus = host.getStatus();
        HostStatus newStatus;
        if( code == 0 )
            newStatus = HostStatus.Running;
        else if (code == 1 )
            newStatus = HostStatus.Shutdown;
        else newStatus = HostStatus.Abnormal;
        if( newStatus == oldStatus ) return;
        host.setStatus(newStatus);
        try {
            hostService.update(host);
        } catch (ItsnowHostException e) {
            logger.warn("Failed to update {} status from {} to {}", host, oldStatus, newStatus);
        }
    }

    public void checkProcessesStatus(){
        Page<ItsnowProcess> processes = processService.findAll(null, new PageRequest(0, 1000));
        for (ItsnowProcess process : processes) {
            checkProcessStatus(process);
        }
    }

    protected void checkProcessStatus(ItsnowProcess process) {
        logger.debug("Checking status of {}({})", process, process.getStatus());
        SystemInvocation invocation = translator.check(process);
        String id = invokeService.addJob(invocation);
        int code = -1;
        try {
            code = invokeService.waitJobFinished(id);
        } catch (SystemInvokeException e) {
            logger.warn("Failed to check status of process: {}, mark it as abnormal, reason: {}", process, e);
        }
        //超时
        // 0: ok
        // 1: stopped
        // 2: abnormal
        // 3: unknown
        // 128: bad host address
        ProcessStatus oldStatus = process.getStatus();
        ProcessStatus newStatus;
        if( code == 0 )
            newStatus = ProcessStatus.Running;
        else if (code == 1 )
            newStatus = ProcessStatus.Stopped;
        else if( code == 2)
            newStatus = ProcessStatus.Abnormal;
        else newStatus = ProcessStatus.Unknown;
        if( newStatus == oldStatus ) return;
        process.setStatus(newStatus);
        try {
            processService.update(process);
        } catch (ItsnowProcessException e) {
            logger.warn("Failed to update {} status from {} to {}", process, oldStatus, newStatus);
        }
    }

}
