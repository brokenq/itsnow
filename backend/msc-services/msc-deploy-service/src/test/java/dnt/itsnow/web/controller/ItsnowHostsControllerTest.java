/**
 * Developer: Kadvin Date: 14-9-15 下午3:49
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.config.DeployControllerConfig;
import dnt.itsnow.model.HostType;
import dnt.itsnow.model.ItsnowHost;
import net.happyonroad.platform.util.DefaultPage;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.service.ItsnowHostService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.itsnow.util.DeployFixture;
import net.happyonroad.support.JsonSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        expect(mockedService.findAllByType("DB")).andReturn(hosts);
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

    @Test
    public void testCheckName() throws Exception {
        expect(mockedService.findByIdAndName(1L, "srv2.itsnow.com")).andReturn(null);
        expect(mockedService.resolveAddress("srv2.itsnow.com")).andReturn("172.16.3.4");
        MockHttpServletRequestBuilder request = get("/admin/api/hosts/checkName?id=1&value=srv2.itsnow.com");
        decorate(request);

        replay(mockedService);
        // 执行
        ResultActions result =this.browser.perform(request);

        // 对业务结果的验证
        decorate(result);
        status().isOk();
        content().string("{\"address\":\"172.16.3.4\"}");
    }

    @Test
    public void testCheckAddress() throws Exception {
        expect(mockedService.findByIdAndAddress(1L, "172.16.3.4")).andReturn(null);
        expect(mockedService.findByIdAndName(1L, "srv2.itsnow.com")).andReturn(null);
        expect(mockedService.resolveName("172.16.3.4")).andReturn("srv2.itsnow.com");
        MockHttpServletRequestBuilder request = get("/admin/api/hosts/checkAddress?id=1&value=172.16.3.4");
        decorate(request);

        replay(mockedService);
        // 执行
        ResultActions result =this.browser.perform(request);

        // 对业务结果的验证
        decorate(result);
        status().isOk();
        content().string("{\"name\":\"srv2.itsnow.com\"}");
    }

    @Test
    public void testCheckPassword() throws Exception {
        expect(mockedService.checkPassword("172.16.3.4", "srv2.itsnow.com", "itsnow@team")).andReturn(true);
        MockHttpServletRequestBuilder request = get("/admin/api/hosts/checkPassword?host=172.16.3.4&username=srv2.itsnow.com&password=itsnow@team");
        decorate(request);

        replay(mockedService);
        // 执行
        ResultActions result =this.browser.perform(request);

        // 对业务结果的验证
        decorate(result);
        status().isOk();
        content().string("{}");
    }

    @Test
    public void testListAvailableByType() throws Exception {
        List<String> types = new ArrayList<String>();
        types.add(HostType.APP.toString());
        types.add(HostType.COM.toString());
        types.add(HostType.DB.toString());
        expect(mockedService.findAllAvailableByType(types)).andReturn(hosts);
        MockHttpServletRequestBuilder request = get("/admin/api/hosts/list_available/APP,COM,DB");
        decorate(request);

        replay(mockedService);
        // 执行
        ResultActions result =this.browser.perform(request);

        // 对业务结果的验证
        decorate(result);
        status().isOk();
    }

}
