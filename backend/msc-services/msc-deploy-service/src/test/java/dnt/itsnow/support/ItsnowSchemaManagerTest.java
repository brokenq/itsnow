/**
 * Developer: Kadvin Date: 14-9-15 下午8:48
 */
package dnt.itsnow.support;

import dnt.itsnow.config.ItsnowSchemaManagerConfig;
import dnt.itsnow.exception.ItsnowSchemaException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.model.SystemInvocation;
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


    @Before
    public void setUp() throws Exception {
        schema = DeployFixture.testSchema();
        schema.setHost(DeployFixture.testHost());
    }

    @After
    public void tearDown() throws Exception {
        verify(systemInvokeService);
        reset(systemInvokeService);
        verify(repository);
        reset(repository);
    }

    @Test
    public void testCreate() throws Exception {
        String jobId = "create-schema-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andReturn(0);
        repository.create(schema);
        expectLastCall().once();

        replay(systemInvokeService);
        replay(repository);

        ItsnowSchema created = manager.create(schema);
        Assert.notNull(created.getCreatedAt());
        Assert.notNull(created.getUpdatedAt());
    }

    @Test
    public void testCreateFailureWhileCanNotCreateRealSchema() throws Exception {
        String jobId = "create-schema-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andThrow(new SystemInvokeException("configuration error"));

        replay(systemInvokeService);
        replay(repository);


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

        replay(systemInvokeService);
        replay(repository);

        manager.delete(schema);
    }

    @Test
    public void testDeleteFailureWhileCanNotDestroyRealSchema() throws Exception {
        String jobId = "delete-schema-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andThrow(new SystemInvokeException("configuration error"));

        replay(systemInvokeService);
        replay(repository);


        try {
            manager.delete(schema);
            throw new Exception("It should failed!");
        } catch (ItsnowSchemaException e) {
            Assert.hasText("Can't drop schema", e.getMessage());
        }
    }
}
