/**
 * Developer: Kadvin Date: 14-9-15 下午8:48
 */
package dnt.itsnow.support;

import dnt.itsnow.config.ItsnowSchemaManagerConfig;
import dnt.itsnow.exception.ItsnowSchemaException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.*;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.ItsnowSchemaRepository;
import dnt.itsnow.service.SystemInvokeService;
import dnt.itsnow.util.DeployFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import static org.easymock.EasyMock.*;

/**
 * Test itsnow schema manager
 */
@ContextConfiguration(classes = ItsnowSchemaManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ItsnowSchemaManagerTest {
    @Autowired
    ItsnowSchemaManager manager;

    @Autowired
    ItsnowSchemaRepository repository;
    @Autowired
    SystemInvokeService    systemInvokeService;

    ItsnowSchema schema;
    PageRequest pageRequest;


    @Before
    public void setUp() throws Exception {
        schema = DeployFixture.testSchema();
        schema.setHost(DeployFixture.testHost());
        pageRequest = new PageRequest(0, 10);
        resetAll();
    }

    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void testCreate() throws Exception {
        String jobId = "create-schema-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andReturn(0);
        repository.create(schema);
        expectLastCall().once();

        replayAll();

        ItsnowSchema created = manager.create(schema);
        Assert.notNull(created.getCreatedAt());
        Assert.notNull(created.getUpdatedAt());
    }

    @Test
    public void testCreateFailureWhileCanNotCreateRealSchema() throws Exception {
        String jobId = "create-schema-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andThrow(new SystemInvokeException("configuration error"));

        replayAll();

        try {
            manager.create(schema);
            throw new Exception("It should failed!");
        } catch (ItsnowSchemaException e) {
            Assert.hasText("Can't create schema", e.getMessage());
        }
    }

    @Test
    public void testDelete() throws Exception {
        String jobId = "delete-schema-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andReturn(0);
        repository.delete(schema);
        expectLastCall().once();

        replayAll();

        manager.delete(schema);
    }

    @Test
    public void testDeleteFailureWhileCanNotDestroyRealSchema() throws Exception {
        String jobId = "delete-schema-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andThrow(new SystemInvokeException("configuration error"));


        replayAll();
        try {
            manager.delete(schema);
            throw new Exception("It should failed!");
        } catch (ItsnowSchemaException e) {
            Assert.hasText("Can't drop schema", e.getMessage());
        }
    }

    @Test
    public void testPickSchema() throws Exception {
        MsuAccount account = new MsuAccount();
        ItsnowHost host = new ItsnowHost();
        host.setType(HostType.COM);
        host.setCapacity(10);
        replayAll();

        //using the app host
        ItsnowSchema pickedSchema = manager.pickSchema(account, host);

        Assert.notNull(pickedSchema.getHost());
        Assert.notNull(pickedSchema.getName());
        Assert.notNull(pickedSchema.getDescription());
        Assert.notNull(pickedSchema.getProperty("user"));
        Assert.notNull(pickedSchema.getProperty("password"));
        Assert.notNull(pickedSchema.getProperty("port"));

    }

    void resetAll(){
        reset(systemInvokeService);
        reset(repository);
    }

    void verifyAll(){
        verify(systemInvokeService);
        verify(repository);
    }

    void replayAll(){
        replay(systemInvokeService);
        replay(repository);
    }
}
