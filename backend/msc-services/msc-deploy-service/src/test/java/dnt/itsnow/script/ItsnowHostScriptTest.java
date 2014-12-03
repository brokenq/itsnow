/**
 * Developer: Kadvin Date: 14-9-23 下午2:10
 */
package dnt.itsnow.script;

import dnt.itsnow.config.DeployScriptConfig;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.repository.ItsnowHostRepository;
import dnt.itsnow.service.SystemInvokeService;
import dnt.itsnow.support.ItsnowHostManager;
import dnt.itsnow.util.DeployFixture;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.easymock.EasyMock.*;

/**
 * <h1>测试主机相关脚本是否可以正确工作</h1>
 *
 * 本测试用例类似于集成测试用例，关注后台与脚本之间的集成
 *
 * 与实际的集成测试用例相比
 * <ol>
 * <li>  只是未涉及WEB层
 * <li>  Repository层采用Mock
 * <li>  另外，也未将系统整体跑起来
 * </ol>
 *
 * 备注：由于本测试用例需要在linux/osx下面运行，预先解除 srv2.itsnow.com 与 测试机器之间的信任关系
 */
@ContextConfiguration(classes = DeployScriptConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
// 跑这个测试用例需要：
//  1. ~/.know_hosts里面不包含 被测试主机(srv2.itsnow.com)
//  2. 被测试主机 不能 信任本机
//  3. 被测试主机 不能 已经安装 mysql/redis
@Ignore("Manual execution")
public class ItsnowHostScriptTest {
    @Autowired
    ItsnowHostManager    manager;
    // Mocked object
    @Autowired
    ItsnowHostRepository repository;

    @Autowired
    SystemInvokeService systemInvokeService;

    ItsnowHost host;


    @Before
    public void setUp() throws Exception {
        System.setProperty("APP_HOME", "/opt/dnt/insight/itsnow/backend/release");
        host = DeployFixture.deployHost();
        manager.start();
    }

    @After
    public void tearDown() throws Exception {
        manager.stop();
        verify(repository);
        reset(repository);
    }

    @Test
    public void testCreateHost() throws Exception {
        repository.create(host);
        expectLastCall().once();
        expect(repository.findByConfiguration(isA(String.class), isA(String.class))).andReturn(host);

        repository.update(host);
        expectLastCall().once();

        replay(repository);


        manager.create(host);
        String invocationId = host.getProperty(ItsnowHostManager.CREATE_INVOCATION_ID);
        Assert.assertNotNull(invocationId);
        systemInvokeService.waitJobFinished(invocationId);

    }

    @Test
    public void testDeleteHost() throws Exception {
        repository.update(host);
        expectLastCall().once();
        repository.deleteByAddress(host.getAddress());
        expectLastCall().once();

        replay(repository);

        manager.delete(host);
        String invocationId = host.getProperty(ItsnowHostManager.DELETE_INVOCATION_ID);
        Assert.assertNotNull(invocationId);

    }
}
