/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.exception.ItsnowSchemaException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.*;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.ItsnowSchemaRepository;
import dnt.itsnow.service.ItsnowHostService;
import dnt.itsnow.service.ItsnowSchemaService;
import dnt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Itsnow Schema Manager</h1>
 */
@Service
@Transactional
public class ItsnowSchemaManager extends ItsnowResourceManager implements ItsnowSchemaService {
    @Autowired
    ItsnowHostService hostService;

    @Override
    public Page<ItsnowSchema> findAll(String keyword, PageRequest pageRequest) {
        logger.debug("Listing itsnow schemas by keyword: {} at {}", keyword, pageRequest);
        int total = repository.countByKeyword(keyword);
        List<ItsnowSchema> hits;
        if (total == 0) {
            hits = new ArrayList<ItsnowSchema>();
        } else {
            if (StringUtils.isNotBlank(keyword)) keyword = "%" + keyword + "%";
            else keyword = null;
            hits = repository.findAllByKeyword(keyword, pageRequest);
        }
        DefaultPage<ItsnowSchema> page = new DefaultPage<ItsnowSchema>(hits, pageRequest, total);
        logger.debug("Listed  itsnow schemas: {}", page);
        return page;
    }

    @Autowired
    ItsnowSchemaRepository repository;

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

    @Override
    public ItsnowSchema create(ItsnowSchema creating) throws ItsnowSchemaException {
        logger.info("Creating itsnow schema: {}", creating);
        SystemInvocation createJob = translator.create(creating);
        createJob.setId("create-schema-" + creating.getName());
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
        dropJob.setId("drop-schema-" + schema.getName());
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
    public ItsnowSchema pickSchema(Account account, ItsnowHost host) throws ItsnowHostException {
        //如果当前应用所在的主机是数据库主机或者混合主机，且由剩余容量，则把schema就近分配则该主机上
        if(host.getType() != HostType.APP /*DB or COMBINED(mixed)*/ && host.getLeftCapacity() > 0 ) {
            return autoAssignSchema(account,host);
        }
        // picked host maybe db or combined
        ItsnowHost dbHost = hostService.pickHost(account, HostType.DB);
        return autoAssignSchema(account,dbHost);
    }

    private ItsnowSchema autoAssignSchema(Account account, ItsnowHost host) {
        ItsnowSchema schema = new ItsnowSchema();
        schema.setHost(host);
        schema.setName("itsnow_" + account.getSn());
        schema.setDescription("schema for " + account.getName());
        schema.setProperty("user", "root");
        String password = UUID.randomUUID().toString().substring(0, 8);
        schema.setProperty("password", password);
        schema.setProperty("port", host.getProperty("db.port", "3306"));
        return schema;
    }

}
