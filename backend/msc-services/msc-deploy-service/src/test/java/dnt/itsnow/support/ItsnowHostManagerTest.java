/**
 * Developer: Kadvin Date: 14-9-15 下午3:50
 */
package dnt.itsnow.support;

import dnt.itsnow.config.ItsnowHostManagerConfig;
import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.model.SystemInvocation;
import dnt.itsnow.repository.ItsnowHostRepository;
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
 * 测试 Itsnow Host Manager
 *
 * 由于find类接口基本是透传，没有什么实际的业务操作，所以暂时不测试
 */
@ContextConfiguration(classes = ItsnowHostManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ItsnowHostManagerTest {
    @Autowired
    ItsnowHostManager    hostManager;
    @Autowired
    ItsnowHostRepository hostRepository;
    @Autowired
    SystemInvokeService  systemInvokeService;

    ItsnowHost host;


    @Before
    public void setUp() throws Exception {
        host = DeployFixture.testHost();
    }

    @After
    public void tearDown() throws Exception {
        verify(systemInvokeService);
        reset(systemInvokeService);

        verify(hostRepository);
        reset(hostRepository);
    }

    @Test
    public void testCreate() throws Exception {
        String jobId = "config-host-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        hostRepository.create(host);
        expectLastCall().once();

        replay(systemInvokeService);
        replay(hostRepository);

        ItsnowHost created = hostManager.create(host);
        Assert.notNull(created.getCreatedAt());
        Assert.notNull(created.getUpdatedAt());
    }

    @Test
    public void testDelete() throws Exception {
        String jobId = "quit-host-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        systemInvokeService.waitJobFinished(jobId);
        expectLastCall().once();
        hostRepository.deleteByAddress(host.getAddress());
        expectLastCall().once();

        replay(systemInvokeService);
        replay(hostRepository);

        hostManager.delete(host);
    }

    @Test
    public void testDeleteFailureWhileCanNotQuitRealHost() throws Exception {
        String jobId = "quit-host-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        systemInvokeService.waitJobFinished(jobId);
        expectLastCall().andThrow(new SystemInvokeException("configuration error"));

        replay(systemInvokeService);
        replay(hostRepository);


        try {
            hostManager.delete(host);
            throw new Exception("It should failed!");
        } catch (ItsnowHostException e) {
            Assert.hasText("Can't quit host", e.getMessage());
        }
    }
}
