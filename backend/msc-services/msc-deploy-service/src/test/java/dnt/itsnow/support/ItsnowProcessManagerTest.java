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
import dnt.itsnow.system.*;
import dnt.itsnow.util.DeployFixture;
import org.apache.commons.io.FileUtils;
import org.easymock.IAnswer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.expectLastCall;

/**
 * <h1>Itsnow Process Manager Test</h1>
 *
 * find*的接口基本就是透传，暂时不需要进行测试
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
        System.setProperty("app.home", System.getProperty("java.io.tmpdir"));
        FileUtils.forceMkdir(new File(System.getProperty("app.home"), "tmp"));
        host = DeployFixture.testHost();
        host.setId(1L);
        schema = DeployFixture.testSchema();
        schema.setId(1L);
        schema.setHost(host);
        process = DeployFixture.testProcess();
        process.setSchema(schema);
        process.setStatus(ProcessStatus.Stopped);
        process.setHost(host);
        process.setAccount(DeployFixture.testAccount());

        resetAll();
    }

    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void testCreate() throws Exception {
        String jobId = "create-process-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
//        expect(systemInvokeService.waitJobFinished(jobId)).andReturn(0) ;
        repository.create(process);
        expectLastCall().once();

        replayAll();

        ItsnowProcess created = service.create(process);
        Assert.assertNotNull(created.getCreatedAt());
        Assert.assertNotNull(created.getUpdatedAt());
    }

    @Test
    public void testDelete() throws Exception {
        String jobId = "delete-process-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andReturn(0) ;
        repository.deleteByName(process.getName());
        expectLastCall().once();
        schemaService.delete(schema);
        expectLastCall().once();

        replayAll();

        service.delete(process);

    }

    @Test
    public void testStart() throws Exception {
        String jobId = "start-process-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        repository.update(process);
        expectLastCall().once();

        replayAll();

        String returnJobId = service.start(process);
        Assert.assertEquals(jobId, returnJobId);
    }

    @Test
    public void testStop() throws Exception {
        process.setStatus(ProcessStatus.Running);
        String jobId = "stop-process-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        repository.update(process);
        expectLastCall().once();

        replayAll();

        String returnJobId = service.stop(process);
        Assert.assertEquals(jobId, returnJobId);
    }

    @Test
    public void testCancel() throws Exception {
        process.setStatus(ProcessStatus.Running);
        String jobId = "stop-process-job-id";
        expect(systemInvokeService.isFinished(jobId)).andReturn(false);
        systemInvokeService.cancelJob(jobId);
        expectLastCall().once();

        replayAll();

        service.cancel(process, jobId);

    }

    @Test
    public void testFollow() throws Exception {
        process.setStatus(ProcessStatus.Stopping);
        String jobId = "stop-process-job-id";
        process.setProperty(ItsnowProcessManager.STOP_INVOCATION_ID, jobId);
        final List<String> messages = new LinkedList<String>();
        final String[] lines = {"one", "two"};
        expect(systemInvokeService.read(jobId, 0, messages)).andAnswer(new IAnswer<Long>() {
            @Override
            public Long answer() throws Throwable {
                messages.add(lines[0]);
                messages.add(lines[1]);
                return 8L;
            }
        });

        replayAll();

        long offset = service.follow(process, jobId, 0L, messages);
        Assert.assertEquals(8L, offset);
        Assert.assertEquals(2, messages.size());
    }

    @Test
    public void testAutoNew() throws Exception {
        MsuAccount account = new MsuAccount();
        account.setStatus(AccountStatus.Valid);
        account.setName("Test MSU");
        account.setSn("msu_111");
        account.setDomain("auto");

        expect(hostService.pickHost(account, HostType.APP)).andAnswer(new IAnswer<ItsnowHost>() {
            @Override
            public ItsnowHost answer() throws Throwable {
                host.setProperty("next.rmi.port", "8110");
                host.setProperty("next.debug.port", "8210");
                host.setProperty("next.jmx.port", "8310");
                host.setProperty("next.http.port", "8410");
                return host;
            }
        });
        schema.setId(null);
        expect(schemaService.pickSchema(account, host)).andReturn(schema);

        replayAll();

        ItsnowProcess newProcess = service.autoNew(account);
        Assert.assertNotNull(newProcess.getName());
        Assert.assertNotNull(newProcess.getDescription());
        Assert.assertNotNull(newProcess.getWd());
        Assert.assertEquals("8110", newProcess.getProperty("rmi.port"));
        Assert.assertEquals("8210", newProcess.getProperty("debug.port"));
        Assert.assertEquals("8310", newProcess.getProperty("jmx.port"));
        Assert.assertEquals("8410", newProcess.getProperty("http.port"));
    }

    @Test
    public void testAutoCreate() throws Exception {
        MsuAccount account = new MsuAccount();
        account.setStatus(AccountStatus.Valid);
        account.setName("Test MSU");
        account.setSn("msu_111");
        account.setDomain("auto");

        expect(hostService.pickHost(account, HostType.APP)).andAnswer(new IAnswer<ItsnowHost>() {
            @Override
            public ItsnowHost answer() throws Throwable {
                host.setProperty("next.rmi.port", "8110");
                host.setProperty("next.debug.port", "8210");
                host.setProperty("next.jmx.port", "8310");
                host.setProperty("next.http.port", "8410");
                return host;
            }
        });
        schema.setId(null);
        expect(schemaService.pickSchema(account, host)).andReturn(schema);
        hostService.update(host);
        expectLastCall().atLeastOnce();
        expect(schemaService.create(schema)).andAnswer(new IAnswer<ItsnowSchema>() {
            @Override
            public ItsnowSchema answer() throws Throwable {
                schema.setId(100L);
                return schema;
            }
        });

        String jobId = "create-process-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        repository.create(isA(ItsnowProcess.class));
        expectLastCall().once();

        expect(systemInvokeService.waitJobFinished(jobId)).andReturn(0);
        repository.update(isA(ItsnowProcess.class));
        expectLastCall().once();

        jobId = "start-process-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        repository.update(isA(ItsnowProcess.class));
        expectLastCall().once();

        replayAll();

        ItsnowProcess newProcess = service.autoCreate(account);
        Assert.assertNotNull(newProcess.getName());
        Assert.assertNotNull(newProcess.getDescription());
        Assert.assertNotNull(newProcess.getWd());
        Assert.assertEquals("8110", newProcess.getProperty("rmi.port"));
        Assert.assertEquals("8210", newProcess.getProperty("debug.port"));
        Assert.assertEquals("8310", newProcess.getProperty("jmx.port"));
        Assert.assertEquals("8410", newProcess.getProperty("http.port"));

        Assert.assertEquals("8111", host.getProperty("next.rmi.port"));
        Assert.assertEquals("8211", host.getProperty("next.debug.port"));
        Assert.assertEquals("8311", host.getProperty("next.jmx.port"));
        Assert.assertEquals("8411", host.getProperty("next.http.port"));
    }

    void resetAll(){
        reset(repository);
        reset(systemInvokeService);
        reset(hostService);
        reset(schemaService);
    }

    void verifyAll(){
        verify(repository);
        verify(systemInvokeService);
        verify(hostService);
        verify(schemaService);
    }

    void replayAll(){
        replay(hostService);
        replay(schemaService);
        replay(systemInvokeService);
        replay(repository);
    }
}
