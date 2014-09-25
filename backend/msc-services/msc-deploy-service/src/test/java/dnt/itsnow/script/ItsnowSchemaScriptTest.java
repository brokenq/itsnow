/**
 * Developer: Kadvin Date: 14-9-23 下午2:10
 */
package dnt.itsnow.script;

import dnt.itsnow.config.DeployScriptConfig;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.repository.ItsnowSchemaRepository;
import dnt.itsnow.service.SystemInvokeService;
import dnt.itsnow.support.ItsnowSchemaManager;
import dnt.itsnow.util.DeployFixture;
import junit.framework.Assert;
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
 * <h1>测试Schema相关脚本是否可以正确工作</h1>
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
//  被测试主机(srv2.itsnow.com) 已经开通
//   mysql/redis is ready
//   msp/msu binaries is ready
@Ignore("Manual execution")
public class ItsnowSchemaScriptTest {
    @Autowired
    ItsnowSchemaManager    manager;
    // Mocked object
    @Autowired
    ItsnowSchemaRepository repository;

    @Autowired
    SystemInvokeService systemInvokeService;

    ItsnowSchema schema;


    @Before
    public void setUp() throws Exception {
        System.setProperty("APP_HOME", "/opt/dnt/insight/itsnow/backend/release");
        schema = DeployFixture.deploySchema();
        manager.start();
    }

    @After
    public void tearDown() throws Exception {
        manager.stop();
        verify(repository);
        reset(repository);
    }

    @Test
    public void testCreate() throws Exception {
        repository.create(schema);
        expectLastCall().once();

        replay(repository);

        manager.create(schema);
        String invocationId = schema.getProperty(ItsnowSchemaManager.CREATE_INVOCATION_ID);
        Assert.assertNotNull(invocationId);
    }

    @Test
    public void testDelete() throws Exception {
        repository.delete(schema);
        expectLastCall().once();

        replay(repository);

        manager.delete(schema);
        String invocationId = schema.getProperty(ItsnowSchemaManager.DELETE_INVOCATION_ID);
        Assert.assertNotNull(invocationId);
    }
}
