/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowSchemaException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.model.SystemInvocation;
import dnt.itsnow.repository.ItsnowSchemaRepository;
import dnt.itsnow.service.ItsnowSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * <h1>Itsnow Schema Manager</h1>
 */
@Service
@Transactional
public class ItsnowSchemaManager extends ItsnowResourceManager implements ItsnowSchemaService {
    @Autowired
    ItsnowSchemaRepository     repository;

    @Override
    public ItsnowSchema create(ItsnowSchema creating) throws ItsnowSchemaException {
        logger.info("Creating itsnow schema: {}", creating);
        SystemInvocation createJob = translator.create(creating);
        String invocationId = invokeService.addJob(createJob);
        try {
            invokeService.waitJobFinished(invocationId);
        } catch (SystemInvokeException e) {
            throw new ItsnowSchemaException("Can't create schema for :" + creating, e);
        }
        creating.setProperty(CREATE_INVOCATION_ID, invocationId);
        creating.creating();
        repository.create(creating);
        logger.info("Created  itsnow schema: {}", creating);
        return creating;
    }

    @Override
    public void delete(ItsnowSchema schema) throws ItsnowSchemaException {
        logger.warn("Deleting itsnow schema: {}", schema);
        SystemInvocation dropJob = translator.drop(schema);
        String invocationId = invokeService.addJob(dropJob);
        schema.setProperty(DELETE_INVOCATION_ID, invocationId);
        try {
            invokeService.waitJobFinished(invocationId);
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

    @Override
    public ItsnowSchema findById(long schemaId) {
        logger.debug("Finding schema by id: {}", schemaId);
        ItsnowSchema schema = repository.findById(schemaId);
        logger.debug("Found   schema by id: {}", schema);
        return schema;
    }
}
