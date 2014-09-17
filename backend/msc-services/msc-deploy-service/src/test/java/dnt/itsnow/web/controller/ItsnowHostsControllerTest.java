/**
 * Developer: Kadvin Date: 14-9-15 下午3:49
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.config.DeployControllerConfig;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.ItsnowHostService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.itsnow.util.DeployFixture;
import dnt.support.JsonSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.LinkedList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Itsnow hosts controller test
 */
@ContextConfiguration(classes = DeployControllerConfig.class)
public class ItsnowHostsControllerTest extends SessionSupportedControllerTest {
    @Autowired
    ItsnowHostService     mockedService;

    ItsnowHost       host;
    List<ItsnowHost> hosts;

    @Before
    public void setUp() throws Exception {
        host = DeployFixture.testHost();
        hosts = new LinkedList<ItsnowHost>();
        hosts.add(host);

        reset(mockedService);
    }

    @After
    public void tearDown() throws Exception {
        verify(mockedService);
        reset(mockedService);
    }

    @Test
    public void testIndex() throws Exception {
        // Service Mock 记录阶段
        expect(mockedService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<ItsnowHost>(hosts));
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/admin/api/hosts");
        decorate(request);

        // Mock 准备播放
        replay(mockedService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result)
                .andExpect(status().isOk())
                .andExpect(header().string("total", "1"))
                .andExpect(header().string("pages", "1"))
                .andExpect(header().string("number", "1"))
                .andExpect(header().string("real", "1"))
                .andExpect(header().string("count", "0"));

    }

    @Test
    public void testDbIndex() throws Exception {
        // Service Mock 记录阶段
        expect(mockedService.findAllDbHosts()).andReturn(hosts);
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/admin/api/hosts/dbs");
        decorate(request);

        // Mock 准备播放
        replay(mockedService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testShow() throws Exception {
        host.setId(102L);
        // Service Mock 记录阶段
        expect(mockedService.findById(host.getId())).andReturn(host);
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/admin/api/hosts/" + host.getId());
        decorate(request);

        // Mock 准备播放
        replay(mockedService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        // Service Mock 记录阶段
        expect(mockedService.create(isA(ItsnowHost.class))).andReturn(host);
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = post("/admin/api/hosts");
        decorate(request).content(JsonSupport.toJSONString(host));

        // Mock 准备播放
        replay(mockedService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testDestroy() throws Exception {
        host.setId(102L);
        // Service Mock 记录阶段
        expect(mockedService.findById(host.getId())).andReturn(host);
        mockedService.delete(host);
        expectLastCall().once();
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = delete("/admin/api/hosts/" + host.getId());
        decorate(request);

        // Mock 准备播放
        replay(mockedService);

        // 执行
        this.browser.perform(request);

        // 对业务结果的验证
        status().isOk();

    }
}
