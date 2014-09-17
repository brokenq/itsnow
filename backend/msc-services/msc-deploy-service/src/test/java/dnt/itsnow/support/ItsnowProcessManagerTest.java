/**
 * Developer: Kadvin Date: 14-9-15 下午3:29
 */
package dnt.itsnow.support;

import dnt.itsnow.config.ItsnowProcessManagerConfig;
import dnt.itsnow.model.*;
import dnt.itsnow.repository.ItsnowProcessRepository;
import dnt.itsnow.service.ItsnowHostService;
import dnt.itsnow.service.ItsnowProcessService;
import dnt.itsnow.service.ItsnowSchemaService;
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

import java.util.Arrays;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.expectLastCall;

/**
 * <h1>Itsnow Process Manager Test</h1>
 *
 * find类的接口基本就是透传，暂时不需要进行测试
 */
@ContextConfiguration(classes = ItsnowProcessManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ItsnowProcessManagerTest {
    //Been test
    @Autowired
    ItsnowProcessService service;

    //mocks
    @Autowired
    ItsnowProcessRepository repository;
    @Autowired
    ItsnowHostService       hostService;
    @Autowired
    ItsnowSchemaService     schemaService;
    @Autowired
    SystemInvokeService     systemInvokeService;

    //Shared instance
    ItsnowProcess process;
    ItsnowHost    host;
    ItsnowSchema  schema;

    @Before
    public void setUp() throws Exception {
        host = DeployFixture.testHost();
        host.setId(1L);
        schema = DeployFixture.testSchema();
        schema.setId(1L);
        process = DeployFixture.testProcess();
        process.setSchema(schema);
        process.setStatus(ProcessStatus.Stopped);
    }

    @After
    public void tearDown() throws Exception {
        verify(repository);
        reset(repository);

        verify(systemInvokeService);
        reset(systemInvokeService);

        verify(hostService);
        reset(hostService);

        verify(schemaService);
        reset(schemaService);
    }

    @Test
    public void testCreate() throws Exception {
        expect(hostService.findById(host.getId())).andReturn(host);
        expect(schemaService.create(schema)).andReturn(schema);
        String jobId = "create-process-job-id";
        expect(systemInvokeService.addJob(isA(SystemJob.class))).andReturn(jobId);
        systemInvokeService.waitJobFinished(jobId);
        expectLastCall().once();
        repository.create(process);
        expectLastCall().once();

        replay(hostService);
        replay(schemaService);
        replay(systemInvokeService);
        replay(repository);

        ItsnowProcess created = service.create(process);
        Assert.notNull(created.getCreatedAt());
        Assert.notNull(created.getUpdatedAt());
    }

    @Test
    public void testDelete() throws Exception {
        schemaService.delete(schema);
        expectLastCall().once();
        String jobId = "delete-process-job-id";
        expect(systemInvokeService.addJob(isA(SystemJob.class))).andReturn(jobId);
        systemInvokeService.waitJobFinished(jobId);
        expectLastCall().once();
        repository.deleteByName(process.getName());
        expectLastCall().once();

        replay(hostService);
        replay(schemaService);
        replay(systemInvokeService);
        replay(repository);

        service.delete(process);

    }

    @Test
    public void testStart() throws Exception {
        String jobId = "start-process-job-id";
        expect(systemInvokeService.addJob(isA(SystemJob.class))).andReturn(jobId);

        replay(hostService);
        replay(schemaService);
        replay(systemInvokeService);
        replay(repository);

        String returnJobId = service.start(process);
        Assert.isTrue(jobId.equals(returnJobId));
    }

    @Test
    public void testStop() throws Exception {
        process.setStatus(ProcessStatus.Running);
        String jobId = "stop-process-job-id";
        expect(systemInvokeService.addJob(isA(SystemJob.class))).andReturn(jobId);

        replay(hostService);
        replay(schemaService);
        replay(systemInvokeService);
        replay(repository);

        String returnJobId = service.stop(process);
        Assert.isTrue(jobId.equals(returnJobId));
    }

    @Test
    public void testCancel() throws Exception {
        process.setStatus(ProcessStatus.Running);
        String jobId = "stop-process-job-id";
        expect(systemInvokeService.isFinished(jobId)).andReturn(false);
        systemInvokeService.cancelJob(jobId);
        expectLastCall().once();

        replay(hostService);
        replay(schemaService);
        replay(systemInvokeService);
        replay(repository);

        service.cancel(process, jobId);

    }

    @Test
    public void testFollow() throws Exception {
        process.setStatus(ProcessStatus.Running);
        String jobId = "stop-process-job-id";
        String[] lines = {"one", "two"};
        expect(systemInvokeService.read(jobId, 0)).andReturn(lines);

        replay(hostService);
        replay(schemaService);
        replay(systemInvokeService);
        replay(repository);

        String[] reads = service.follow(process, jobId, 0);
        Assert.isTrue(Arrays.equals(lines, reads));
    }
}