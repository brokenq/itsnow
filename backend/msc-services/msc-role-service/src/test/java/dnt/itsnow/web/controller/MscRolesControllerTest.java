package dnt.itsnow.web.controller;

import dnt.itsnow.config.MscRolesControllerConfig;
import dnt.itsnow.model.MscRole;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.MscRoleService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.support.JsonSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = MscRolesControllerConfig.class)
//@Ignore("By xiongjie, for 9/30 demo integration")
public class MscRolesControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    MscRoleService mscRoleService;

    MscRole role;

    List<MscRole> roles;

    @Before
    public void setup() {

        role = new MscRole();
        role.setId(1L);
        role.setName("ROLE_MONITOR");
        role.setDescription("This is a test.");
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());

        roles = new ArrayList<MscRole>();
        roles.add(role);

        reset(mscRoleService);
    }

    @Test
    public void testIndex() throws Exception {

        expect(mscRoleService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<MscRole>(roles));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/msc-roles");
        request = decorate(request);

        replay(mscRoleService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testShow() throws Exception {

        expect(mscRoleService.findAllRelevantInfo(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<MscRole>(roles));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/msc-roles/detail/ROLE_MONITOR");
        request = decorate(request);

        replay(mscRoleService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testUpdate() throws Exception {
        expect(mscRoleService.findByName("ROLE_MONITOR")).andReturn(role);
        expect(mscRoleService.update(anyObject(MscRole.class))).andReturn(role);
        replay(mscRoleService);

        MockHttpServletRequestBuilder request = put("/api/msc-roles/ROLE_MONITOR").content(accountJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testDestroy() throws Exception {
        expect(mscRoleService.findByName("ROLE_MONITOR")).andReturn(role);
        expect(mscRoleService.destroy(anyObject(MscRole.class))).andReturn(role);
        expectLastCall().once();

        replay(mscRoleService);

        URI uri = new URI("/api/msc-roles/ROLE_MONITOR");

        MockHttpServletRequestBuilder request = delete(uri);
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());

    }

    protected String accountJson(){
        return JsonSupport.toJSONString(role);
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(mscRoleService);
    }

}
