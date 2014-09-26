/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowProcessException;
import dnt.itsnow.exception.ItsnowSchemaException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.*;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.ItsnowProcessRepository;
import dnt.itsnow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>Itsnow Process Manager</h1>
 */
@Service
@Transactional
public class ItsnowProcessManager extends ItsnowResourceManager implements ItsnowProcessService {
    public static final String START_INVOCATION_ID = "startInvocationId";
    public static final String STOP_INVOCATION_ID = "stopInvocationId";

    private static final int DEPLOY_FLAG = 1;
    private static final int UNDEPLOY_FLAG = -1;
    private static final int START_FLAG = 2;
    private static final int STOP_FLAG = -2;

    @Autowired
    ItsnowProcessRepository    repository;
    @Autowired
    ItsnowHostService          hostService;
    @Autowired
    ItsnowSchemaService        schemaService;

    @Override
    public Page<ItsnowProcess> findAll(String keyword, PageRequest request) {
        logger.debug("Listing itsnow process by keyword: {} at {}", keyword, request);
        int total = repository.countByKeyword(keyword);
        List<ItsnowProcess> hits = repository.findAllByKeyword(keyword, request);
        return new DefaultPage<ItsnowProcess>(hits, request, total);
    }

    @Override
    public ItsnowProcess findByName(String name) {
        logger.debug("Finding itsnow process by name: {}", name);
        return repository.findByName(name);
    }

    @Override
    public ItsnowProcess create(ItsnowProcess creating) throws ItsnowProcessException {
        logger.info("Creating itsnow process: {}", creating);

        ItsnowHost host;
        if( creating.getHost() != null )
            host = creating.getHost();
        else if( creating.getSchema().getHost() != null )
            host = creating.getSchema().getHost();
        else
            host = hostService.findById(creating.getHostId());

        if (host == null)
            throw new ItsnowProcessException("Can't find itsnow host with id = " + creating.getHostId() );
        creating.setHost(host);

        //要求schema service 创建相应的schema
        try {
            ItsnowSchema schema = schemaService.create(creating.getSchema());
            creating.setSchema(schema);
        } catch (ItsnowSchemaException e) {
            throw new ItsnowProcessException("Can't create schema " + creating.getSchema().getName()
                                             + " for process " + creating.getName(), e );
        }

        SystemInvocation deployJob = translator.deploy(creating);
        deployJob.setUserFlag(DEPLOY_FLAG);
        String invocationId = invokeService.addJob(deployJob);
        try {
            //因为部署一个新系统是一件比较快速的事情，所以设计为
            // 任务完成才会返回，如果任务失败，则抛出异常
            invokeService.waitJobFinished(invocationId);
        } catch (SystemInvokeException e) {
            throw new ItsnowProcessException("Can't deploy itsnow process for " + creating, e);
        }
        creating.setStatus(ProcessStatus.Stopped);
        creating.creating();
        creating.setProperty(CREATE_INVOCATION_ID, invocationId);
        repository.create(creating);
        logger.info("Created  itsnow process: {}", creating);
        return creating;
    }

    @Override
    public void delete(ItsnowProcess process) throws ItsnowProcessException {
        logger.warn("Deleting itsnow process: {}", process);
        if( process.getStatus() != ProcessStatus.Stopped)
            throw new ItsnowProcessException("Can't destroy the non-stopped process with status = " + process.getStatus());
        try {
            schemaService.delete(process.getSchema());
        } catch (ItsnowSchemaException e) {
            throw new ItsnowProcessException("Can't destroy the schema used by the process", e);
        }

        SystemInvocation undeployJob = translator.undeploy(process);
        undeployJob.setUserFlag(UNDEPLOY_FLAG);
        String invocationId = invokeService.addJob(undeployJob);
        process.setProperty(DELETE_INVOCATION_ID, invocationId);
        try{
            invokeService.waitJobFinished(invocationId);
        }catch (SystemInvokeException e){
            throw new ItsnowProcessException("Can't un-deploy itsnow process for {}", e);
        }
        repository.deleteByName(process.getName());
        logger.warn("Deleted  itsnow process: {}", process);
    }

    @Override
    public String start(ItsnowProcess process) throws ItsnowProcessException {
        logger.info("Starting {}", process.getName());
        if( process.getStatus() != ProcessStatus.Stopped)
            throw new ItsnowProcessException("Can't start the process with status = " + process.getStatus());
        // 因为启动一个系统可能是一个比较慢的事情，所以采用异步方式，任务启动之后就返回
        SystemInvocation startJob = translator.start(process);
        startJob.setUserFlag(START_FLAG);
        process.updating();
        String invocationId = invokeService.addJob(startJob);
        process.setProperty(START_INVOCATION_ID, invocationId);
        repository.update(process);
        return invocationId;
    }

    @Override
    public String stop(ItsnowProcess process) throws ItsnowProcessException {
        logger.info("Stopping {}", process.getName());
        if( process.getStatus() == ProcessStatus.Stopped || process.getStatus() == ProcessStatus.Stopping)
            throw new ItsnowProcessException("Can't stop the " + process.getStatus() + " process");
        SystemInvocation stopJob = translator.stop(process);
        stopJob.setUserFlag(STOP_FLAG);
        process.updating();
        String invocationId = invokeService.addJob(stopJob);
        process.setProperty(STOP_INVOCATION_ID, invocationId);
        repository.update(process);
        return invocationId;
    }

    @Override
    public void cancel(ItsnowProcess process, String jobId) throws ItsnowProcessException {
        boolean finished = invokeService.isFinished(jobId);
        if( finished )
            throw new ItsnowProcessException("The job " + jobId + " is finished already!");
        invokeService.cancelJob(jobId);
    }

    @Override
    public long follow(ItsnowProcess process, String jobId, long offset, List<String> result) {
        logger.trace("Follow {}'s job: {}", process, jobId);
        return invokeService.read(jobId, offset, result);
    }

    @Override
    public void started(SystemInvocation invocation) {
        super.started(invocation);
        if( invocation.getUserFlag() == START_FLAG){
            // set process status as starting
            updateStatus(START_INVOCATION_ID, invocation.getId(), ProcessStatus.Starting);
        }else if (invocation.getUserFlag() == STOP_FLAG){
            // set process status as stopping
            updateStatus(STOP_INVOCATION_ID, invocation.getId(), ProcessStatus.Stopping);
        }
    }

    @Override
    public void finished(SystemInvocation invocation) {
        super.finished(invocation);
        if( invocation.getUserFlag() == START_FLAG){
            // set process status as started
            updateStatus(START_INVOCATION_ID, invocation.getId(), ProcessStatus.Running);
        }else if (invocation.getUserFlag() == STOP_FLAG){
            // set process status as stopped
            updateStatus(STOP_INVOCATION_ID, invocation.getId(), ProcessStatus.Stopped);
        }
    }

    @Override
    public void failed(SystemInvocation invocation, SystemInvokeException e) {
        super.failed(invocation, e);
        //set process status as abnormal
        if( invocation.getUserFlag() == START_FLAG){
            // set process status as started
            updateStatus(START_INVOCATION_ID, invocation.getId(), ProcessStatus.Abnormal);
        }else if (invocation.getUserFlag() == STOP_FLAG){
            // set process status as stopped
            updateStatus(STOP_INVOCATION_ID, invocation.getId(), ProcessStatus.Abnormal);
        }
    }

    protected void updateStatus(String invocationName, String invocationId, ProcessStatus status) {
        ItsnowProcess process = repository.findByConfiguration(invocationName, invocationId);
        if( process == null ) return;//听到了别的消息，忽略
        logger.info("Update {} status as {}", process, status);
        process.setStatus(status);
        process.updating();
        repository.update(process);
    }
}
