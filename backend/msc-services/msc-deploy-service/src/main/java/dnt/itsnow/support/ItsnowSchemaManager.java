/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowSchemaException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.model.SystemJob;
import dnt.itsnow.repository.ItsnowSchemaRepository;
import dnt.itsnow.service.ItsnowSchemaService;
import dnt.itsnow.service.SystemInvokeService;
import dnt.itsnow.service.SystemJobService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <h1>Itsnow Schema Manager</h1>
 */
@Service
public class ItsnowSchemaManager extends Bean implements ItsnowSchemaService {
    @Autowired
    ItsnowSchemaRepository repository;
    @Autowired
    SystemJobService       systemJobService;
    @Autowired
    SystemInvokeService    systemInvokeService;

    @Override
    public ItsnowSchema create(ItsnowSchema creating) throws ItsnowSchemaException {
        logger.info("Creating itsnow schema: {}", creating);
        SystemJob createJob = systemJobService.create(creating);
        String job = systemInvokeService.addJob(createJob);
        try {
            systemInvokeService.waitJobFinished(job);
        } catch (SystemInvokeException e) {
            throw new ItsnowSchemaException("Can't create schema for :" + creating, e);
        }
        creating.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        creating.setUpdatedAt(creating.getUpdatedAt());
        repository.create(creating);
        logger.info("Created  itsnow schema: {}", creating);
        return creating;
    }

    @Override
    public void delete(ItsnowSchema schema) throws ItsnowSchemaException {
        logger.warn("Deleting itsnow schema: {}", schema);
        SystemJob dropJob = systemJobService.drop(schema);
        String job = systemInvokeService.addJob(dropJob);
        try {
            systemInvokeService.waitJobFinished(job);
        } catch (SystemInvokeException e) {
            throw new ItsnowSchemaException("Can't drop schema for :" + schema, e);
        }
        repository.delete(schema);
        logger.warn("Deleted  itsnow schema: {}", schema);
    }

    @Override
    public ItsnowSchema findByName(String name) {
        logger.debug("Finding schema by name: {}", name);
        ItsnowSchema schema = repository.findByName(name);
        logger.debug("Found   schema by name: {}", schema);
        return schema;
    }
}
