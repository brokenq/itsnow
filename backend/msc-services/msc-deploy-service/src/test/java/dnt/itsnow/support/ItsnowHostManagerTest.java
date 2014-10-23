/**
 * Developer: Kadvin Date: 14-9-15 下午3:50
 */
package dnt.itsnow.support;

import dnt.itsnow.config.ItsnowHostManagerConfig;
import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.*;
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

import java.util.ArrayList;
import java.util.List;

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
    ItsnowHostRepository repository;
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

        verify(repository);
        reset(repository);
    }

    @Test
    public void testCreate() throws Exception {
        String jobId = "config-host-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        repository.create(host);
        expectLastCall().once();

        replay(systemInvokeService);
        replay(repository);

        ItsnowHost created = hostManager.create(host);
        Assert.notNull(created.getCreatedAt());
        Assert.notNull(created.getUpdatedAt());
    }

    @Test
    public void testDelete() throws Exception {
        String jobId = "quit-host-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andReturn(0);
        repository.update(host);
        expectLastCall().once();
        repository.deleteByAddress(host.getAddress());
        expectLastCall().once();

        replay(systemInvokeService);
        replay(repository);

        hostManager.delete(host);
    }

    @Test
    public void testDeleteFailureWhileCanNotQuitRealHost() throws Exception {
        String jobId = "quit-host-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andThrow(new SystemInvokeException("configuration error"));
        repository.update(host);
        expectLastCall().once();

        replay(systemInvokeService);
        replay(repository);


        try {
            hostManager.delete(host);
            throw new Exception("It should failed!");
        } catch (ItsnowHostException e) {
            Assert.hasText("Can't quit host", e.getMessage());
        }
    }

    @Test
    public void testResolveAddress() throws Exception {
        replay(systemInvokeService);
        replay(repository);

        Assert.isTrue("172.16.3.4".equals(hostManager.resolveAddress("srv2.itsnow.com")));
        Assert.isTrue("172.16.3.4".equals(hostManager.resolveAddress("srv2")));
        try {
            hostManager.resolveAddress("no-routes");
        } catch (ItsnowHostException e) {
            Assert.isTrue(e.getMessage().contains("Bad host name"));
        }
    }

    @Test
    public void testResolveName() throws Exception {
        replay(systemInvokeService);
        replay(repository);

        String hostName = hostManager.resolveName("172.16.3.4");
        Assert.isTrue("srv2.itsnow.com".equals(hostName));
    }

    @Test
    public void testPickHost() throws Exception {
        ItsnowHost host1 = new ItsnowHost();
        host1.setCapacity(10);
        host1.setProcessesCount(3);
        host1.setSchemasCount(4);
        ItsnowHost host2 = new ItsnowHost();

        host2.setCapacity(8);
        host2.setProcessesCount(3);
        host2.setSchemasCount(4);

        List<ItsnowHost> hosts = new ArrayList<ItsnowHost>();
        hosts.add(host1);
        hosts.add(host2);

        expect(repository.findAllByType(HostType.APP)).andReturn(hosts);
        expect(repository.findAllByType(HostType.COM)).andReturn(new ArrayList<ItsnowHost>());

        replay(systemInvokeService);
        replay(repository);

        ItsnowHost host = hostManager.pickHost(new MspAccount(), HostType.APP );
        Assert.isTrue(host == host1);
    }

    public void testCheckPassword() throws Exception {
        String jobId = "check-password-job-id";
        expect(systemInvokeService.addJob(isA(SystemInvocation.class))).andReturn(jobId);
        expect(systemInvokeService.waitJobFinished(jobId)).andReturn(0);

        replay(systemInvokeService);
        replay(repository);
        boolean b = hostManager.checkPassword("172.16.3.4", "srv2.itsnow.com", "itsnow@team");
        Assert.isTrue(b);
    }
}
