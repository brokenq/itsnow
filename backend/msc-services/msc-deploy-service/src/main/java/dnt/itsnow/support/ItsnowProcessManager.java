/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.exception.ItsnowProcessException;
import dnt.itsnow.exception.ItsnowSchemaException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.*;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.util.DefaultPage;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.repository.ItsnowProcessRepository;
import dnt.itsnow.service.ItsnowHostService;
import dnt.itsnow.service.ItsnowProcessService;
import dnt.itsnow.service.ItsnowSchemaService;
import dnt.itsnow.service.SystemInvocationTranslator;
import net.happyonroad.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Itsnow Process Manager</h1>
 */
@Service
public class ItsnowProcessManager extends ItsnowResourceManager implements ItsnowProcessService {

    public static final String DEPLOY = "deploy";
    public static final String START = "start";
    public static final String STOP = "stop";

    public static final String DEPLOY_INVOCATION_ID = "createInvocationId";
    public static final String START_INVOCATION_ID = "startInvocationId";
    public static final String STOP_INVOCATION_ID = "stopInvocationId";

    static final String DEPLOY_PROCESS_PRFIX = "deploy-process-";
    static final String UNDEPLOY_PROCESS_PRFIX = "undeploy-process-";
    static final String START_PROCESS_PRFIX = "start-process-";
    static final String STOP_PROCESS_PRFIX = "stop-process-";

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
    @Autowired
    SystemInvocationTranslator translator;

    @Override
    public Page<ItsnowProcess> findAll(String keyword, PageRequest request) {
        logger.debug("Listing itsnow processes by keyword: {} at {}", keyword, request);
        int total = repository.countByKeyword(keyword);
        List<ItsnowProcess> hits;
        if( total == 0 ){
            hits = new ArrayList<ItsnowProcess>();
        }else{
            if(StringUtils.isNotBlank(keyword)) keyword = "%" + keyword + "%";
            else keyword = null;
            hits = repository.findAllByKeyword(keyword, request);
        }
        DefaultPage<ItsnowProcess> page = new DefaultPage<ItsnowProcess>(hits, request, total);
        logger.debug("Listed  itsnow processes {}", page);
        return page;
    }

    @Override
    public ItsnowProcess findByName(String name) {
        logger.debug("Finding itsnow process by name: {}", name);
        ItsnowProcess process = repository.findByName(name);
        logger.debug("Found   itsnow process {}", process);
        return process;
    }

    @Override
    public ItsnowProcess create(ItsnowProcess creating) throws ItsnowProcessException {
        logger.info("Creating itsnow process: {}", creating);
        // 设定如下前提:
        //   由外部/控制器,测试程序负责完成 process 模型的准备
        // (主要是process -> account, process -> host, process -> schema, schema -> host)
        if (creating.getHost() == null)
            throw new ItsnowProcessException("You must specify the host where run the process " + creating );
        if (creating.getSchema() == null )
            throw new ItsnowProcessException("You must specify the schema where store the data for " + creating );

        // 支持两种schema情况，一种是已经创建好的
        // 一种是界面指定的，这将会自动通过 schema service 创建相应的schema对象
        if( creating.getSchema().isNew()) {
            try {
                ItsnowSchema schema = schemaService.create(creating.getSchema());
                creating.setSchemaId(schema.getId());
            } catch (ItsnowSchemaException e) {
                throw new ItsnowProcessException("Can't create schema " + creating.getSchema().getName()
                                                 + " for process " + creating.getName(), e );
            }
        }

        SystemInvocation deployJob = translator.deploy(creating);
        deployJob.setId(DEPLOY_PROCESS_PRFIX + creating.getName());
        deployJob.setUserFlag(DEPLOY_FLAG);
        String invocationId = invokeService.addJob(deployJob);
        creating.setStatus(ProcessStatus.Deploying);
        creating.creating();
        creating.setProperty(CREATE_INVOCATION_ID, invocationId);
        repository.create(creating);
        logger.info("Created  itsnow process: {}", creating);
        return creating;
    }

    @Override
    public ItsnowProcess autoNew(Account account) throws ItsnowProcessException {
        logger.info("Suggesting itsnow process for {}", account);
        if( account.getStatus() != AccountStatus.Valid)
            throw new ItsnowProcessException("Can't auto new process for account not valid");
        if( account.getProcess() != null )
            throw new ItsnowProcessException("Can't auto new process for account with process");
        ItsnowHost host;
        try {
            host = autoAssignHost(account);
        } catch (ItsnowHostException e) {
            throw new ItsnowProcessException(e.getMessage(), e);
        }
        ItsnowSchema schema;
        try {
            schema = autoAssignSchema(account, host);
        } catch (ItsnowHostException e) {
            throw new ItsnowProcessException(e.getMessage(), e );
        }
        ItsnowProcess process = autoAssignProcess(account, host, schema);
        logger.info("Suggested  itsnow process for {}", account);
        return process;
    }

    @Override
    public ItsnowProcess autoCreate(Account account) throws ItsnowProcessException {
        logger.info("Auto creating itsnow process for {}", account);
        ItsnowProcess process = autoNew(account);
        updateHost(process.getHost(), process);
        // Create it first
        process = create(process);
        // Then start it
        try {
            int code = invokeService.waitJobFinished(process.getProperty(DEPLOY_INVOCATION_ID));
            if (0 == code) {
                process.setStatus(ProcessStatus.Stopped);
                update(process);
            }
        } catch (SystemInvokeException e) {
            throw new ItsnowProcessException("Can't deploy itsnow process for " + process, e);
        }
        start(process);
        logger.info("Auto created and start the itsnow process {} for {}", process, account);
        return process;
    }

    @Override
    public void delete(ItsnowProcess process) throws ItsnowProcessException {
        logger.warn("Deleting itsnow process: {}", process);
        if( process.getStatus() != ProcessStatus.Stopped)
            throw new ItsnowProcessException("Can't destroy the non-stopped process with status = " + process.getStatus());
        SystemInvocation undeployJob = translator.undeploy(process);
        undeployJob.setId(UNDEPLOY_PROCESS_PRFIX + process.getName());
        undeployJob.setUserFlag(UNDEPLOY_FLAG);
        String invocationId = invokeService.addJob(undeployJob);
        process.setProperty(DELETE_INVOCATION_ID, invocationId);
        try{
            invokeService.waitJobFinished(invocationId);
        }catch (SystemInvokeException e){
            throw new ItsnowProcessException("Can't un-deploy itsnow process for {}", e);
        }
        repository.deleteByName(process.getName());
        try {
            schemaService.delete(process.getSchema());
        } catch (ItsnowSchemaException e) {
            throw new ItsnowProcessException("Can't destroy the schema used by the process", e);
        }

        logger.warn("Deleted  itsnow process: {}", process);
    }

    @Override
    public void update(ItsnowProcess process) throws ItsnowProcessException {
        logger.info("Updating {}", process);
        process.updating();
        repository.update(process);
        logger.info("Updated  {}", process);
    }

    @Override
    public String start(ItsnowProcess process) throws ItsnowProcessException {
        logger.info("Starting {}", process);
        if( process.getStatus() != ProcessStatus.Stopped)
            throw new ItsnowProcessException("Can't start the process with status = " + process.getStatus());
        // 因为启动一个系统可能是一个比较慢的事情，所以采用异步方式，任务启动之后就返回
        SystemInvocation startJob = translator.start(process);
        startJob.setId(START_PROCESS_PRFIX + process.getName());
        startJob.setUserFlag(START_FLAG);
        process.updating();
        String invocationId = invokeService.addJob(startJob);
        process.setProperty(START_INVOCATION_ID, invocationId);
        repository.update(process);
        logger.debug("Starting {} with invocation id {}", process, invocationId);
        return invocationId;
    }

    @Override
    public String stop(ItsnowProcess process) throws ItsnowProcessException {
        logger.info("Stopping {}", process);
        if( process.getStatus() == ProcessStatus.Stopped || process.getStatus() == ProcessStatus.Stopping)
            throw new ItsnowProcessException("Can't stop the " + process.getStatus() + " process");
        SystemInvocation stopJob = translator.stop(process);
        stopJob.setId(STOP_PROCESS_PRFIX + process.getName());
        stopJob.setUserFlag(STOP_FLAG);
        process.updating();
        String invocationId = invokeService.addJob(stopJob);
        process.setProperty(STOP_INVOCATION_ID, invocationId);
        repository.update(process);
        logger.debug("Stopping {} with invocation id {}", process, invocationId);
        return invocationId;
    }

    @Override
    public void cancel(ItsnowProcess process, String jobId) throws ItsnowProcessException {
        boolean finished = invokeService.isFinished(jobId);
        if( finished )
            throw new ItsnowProcessException("The job " + jobId + " is finished already!");
        invokeService.cancelJob(jobId);
        logger.debug("{} invocation {} is cancelled", process, jobId);
    }

    @Override
    public long follow(ItsnowProcess process, String jobId, long offset, List<String> result) {
        logger.trace("Follow {}'s job: {}", process, jobId);

        if(jobId.equals(process.getProperty(DEPLOY_INVOCATION_ID))) {
            return invokeService.read(jobId, offset, result);
        }else if(jobId.equals(process.getProperty(START_INVOCATION_ID))){
            return invokeService.read(jobId, offset, result);
        }else if (jobId.equals(process.getProperty(STOP_INVOCATION_ID))){
            return invokeService.read(jobId, offset, result);
        }
        return -1;
    }

    @Override
    public ItsnowProcess waitFinished(String name, String job) throws ItsnowProcessException{
        String type = job.indexOf(START) == 0 ? START : job.indexOf(STOP) == 0 ? STOP : "";
        if( !invokeService.isFinished(job) ){
            try {
                invokeService.waitJobFinished(job);
            } catch (SystemInvokeException e) {
                throw new ItsnowProcessException("Error while wait the job " + job + " finished");
            }
        }
        ItsnowProcess process = repository.findByName(name);
        if(process == null) throw new ItsnowProcessException("The process name " + name + " is invalid");
        if( type.equals(START) && process.getStatus() != ProcessStatus.Running){
            throw new ItsnowProcessException("The process is not state in Running, but: " + process.getStatus());
        }
        if( type.equals(STOP) && process.getStatus() != ProcessStatus.Stopped){
            throw new ItsnowProcessException("The process is not state in Stopped, but: " + process.getStatus());
        }
        return process;
    }

    @Override
    public boolean care(SystemInvocation invocation) {
        String id = invocation.getId();
        return id.startsWith(DEPLOY_PROCESS_PRFIX) || id.startsWith(UNDEPLOY_PROCESS_PRFIX)
               || id.startsWith(START_PROCESS_PRFIX) || id.startsWith(STOP_PROCESS_PRFIX);
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
            ItsnowProcess process = updateStatus(START_INVOCATION_ID, invocation.getId(), ProcessStatus.Running);
            if( process != null ) {
                logger.info("Started  {}", process);
            }
        }else if (invocation.getUserFlag() == STOP_FLAG){
            // set process status as stopped
            ItsnowProcess process = updateStatus(STOP_INVOCATION_ID, invocation.getId(), ProcessStatus.Stopped);
            if( process != null ) {
                logger.info("Stopped  {}", process);
            }
        }
    }

    @Override
    public void failed(SystemInvocation invocation, SystemInvokeException e) {
        super.failed(invocation, e);
        //set process status as abnormal
        if( invocation.getUserFlag() == START_FLAG){
            // set process status as started
            ItsnowProcess process = updateStatus(START_INVOCATION_ID, invocation.getId(), ProcessStatus.Abnormal);
            if( process != null ) {
                logger.info("Starts   {} failed", process);
            }
        }else if (invocation.getUserFlag() == STOP_FLAG){
            // set process status as stopped
            ItsnowProcess process = updateStatus(STOP_INVOCATION_ID, invocation.getId(), ProcessStatus.Abnormal);
            if( process != null ) {
                logger.info("Stops    {} failed", process);
            }
        }
    }

    protected ItsnowProcess updateStatus(String invocationName, String invocationId, ProcessStatus status) {
        ItsnowProcess process = repository.findByConfiguration(invocationName, invocationId);
        if( process == null ) return null;//听到了别的消息，忽略
        logger.info("Update {} status as {}", process, status);
        process.setStatus(status);
        process.updating();
        repository.update(process);
        return process;
    }

    ItsnowHost autoAssignHost(Account account) throws ItsnowHostException {
        return hostService.pickHost(account, HostType.APP );
    }

    ItsnowSchema autoAssignSchema(Account account, ItsnowHost host) throws ItsnowHostException {
        return schemaService.pickSchema(account, host);
    }

    ItsnowProcess autoAssignProcess(Account account, ItsnowHost host,  ItsnowSchema schema) {
        ItsnowProcess process = new ItsnowProcess();
        process.setAccount(account);
        process.setHost(host);
        process.setSchema(schema);

        process.setName("itsnow_" + account.getSn());
        process.setDescription("Itsnow process for " + account.getSn());
        process.setWd("/opt/itsnow/" + process.getName());
        process.setProperty("rmi.port", host.getProperty("next.rmi.port", "8101"));
        process.setProperty("debug.port", host.getProperty("next.debug.port", "8201"));
        process.setProperty("jmx.port", host.getProperty("next.jmx.port", "8301"));
        process.setProperty("http.port", host.getProperty("next.http.port", "8401"));
        return process;
    }

    void updateHost(ItsnowHost host, ItsnowProcess process) throws ItsnowProcessException {
        host.setProperty("next.rmi.port", next(process.getProperty("rmi.port")));
        host.setProperty("next.debug.port", next(process.getProperty("debug.port")));
        host.setProperty("next.jmx.port", next(process.getProperty("jmx.port")));
        host.setProperty("next.http.port", next(process.getProperty("http.port")));
        try {
            hostService.update(host);
        } catch (ItsnowHostException e) {
            throw new ItsnowProcessException("Can't update the host for next ports", e);
        }
    }

    private String next(String value) {
        return String.valueOf(Integer.valueOf(value) + 1);
    }

}
