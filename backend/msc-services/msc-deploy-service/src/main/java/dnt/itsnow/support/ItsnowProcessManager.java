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
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>Itsnow Process Manager</h1>
 */
@Service
public class ItsnowProcessManager extends Bean implements ItsnowProcessService {
    @Autowired
    ItsnowProcessRepository repository;
    @Autowired
    SystemJobService        systemJobService;
    @Autowired
    SystemInvokeService     systemInvokeService;
    @Autowired
    ItsnowHostService       hostService;
    @Autowired
    ItsnowSchemaService     schemaService;

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
        ItsnowHost host = hostService.findById(creating.getHostId());
        if (host == null)
            throw new ItsnowProcessException("Can't find itsnow host with id = " + creating.getHostId() );
        creating.setHost(host);
        SystemJob deployJob = systemJobService.deploy(creating);
        String jobId = systemInvokeService.addJob(deployJob);
        try {
            //任务完成才会返回，如果任务失败，则抛出异常
            systemInvokeService.waitJobFinished(jobId);
        } catch (SystemInvokeException e) {
            throw new ItsnowProcessException("Can't deploy itsnow process for " + creating, e);
        }
        //要求schema service 创建相应的schema
        try {
            ItsnowSchema schema = schemaService.create(creating.getSchema());
            creating.setSchema(schema);
        } catch (ItsnowSchemaException e) {
            throw new ItsnowProcessException("Can't create schema for process", e );
        }

        creating.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        creating.setUpdatedAt(creating.getCreatedAt());
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

        SystemJob undeployJob = systemJobService.undeploy(process);
        String jobId = systemInvokeService.addJob(undeployJob);
        try{
            systemInvokeService.waitJobFinished(jobId);
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
        SystemJob startJob = systemJobService.start(process);
        return systemInvokeService.addJob(startJob);
    }

    @Override
    public String stop(ItsnowProcess process) throws ItsnowProcessException {
        logger.info("Stopping {}", process.getName());
        if( process.getStatus() == ProcessStatus.Stopped)
            throw new ItsnowProcessException("Can't stop the stopped process");
        if( process.getStatus() == ProcessStatus.Stopping)
            throw new ItsnowProcessException("Can't stop the stopping process");
        SystemJob stopJob = systemJobService.stop(process);
        return systemInvokeService.addJob(stopJob);
    }

    @Override
    public void cancel(ItsnowProcess process, String job) throws ItsnowProcessException {
        boolean finished = systemInvokeService.isFinished(job);
        if( finished )
            throw new ItsnowProcessException("The job " + job + " is finished already!");
        systemInvokeService.cancelJob(job);
    }

    @Override
    public String[] follow(ItsnowProcess process, String job, int offset) {
        return systemInvokeService.read(job, offset);
    }
}
