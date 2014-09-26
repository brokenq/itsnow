package itsnow.dnt.web.controller;

 import dnt.itsnow.model.GeneralRole;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.RoleService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.support.JsonSupport;
import itsnow.dnt.config.RolesControllerConfig;
import org.junit.After;
import org.junit.Before;
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

@ContextConfiguration(classes = RolesControllerConfig.class)
public class RolesControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    RoleService roleService;

    GeneralRole role;

    List<GeneralRole> roles;

    @Before
    public void setup() {

        role = new GeneralRole();
        role.setId(1L);
        role.setName("ROLE_MONITOR");
        role.setDescription("This is a test.");
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());

        roles = new ArrayList<GeneralRole>();
        roles.add(role);

        reset(roleService);
    }

    @Test
    public void testIndex() throws Exception {

        expect(roleService.findAll(anyLong(), anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<GeneralRole>(roles));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/roles");
        request = decorate(request);

        replay(roleService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testShow() throws Exception {

        expect(roleService.findAllRelevantInfo(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<GeneralRole>(roles));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/roles/ROLE_MONITOR");
        request = decorate(request);

        replay(roleService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testUpdate() throws Exception {
        expect(roleService.findByName("ROLE_MONITOR")).andReturn(role);
        expect(roleService.update(anyObject(GeneralRole.class))).andReturn(role);
        replay(roleService);

        MockHttpServletRequestBuilder request = put("/api/roles/ROLE_MONITOR").content(accountJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testDestroy() throws Exception {
        expect(roleService.findByName("ROLE_MONITOR")).andReturn(role);
        expect(roleService.destroy(anyObject(GeneralRole.class))).andReturn(role);
        expectLastCall().once();

        replay(roleService);

        URI uri = new URI("/api/roles/ROLE_MONITOR");

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
        verify(roleService);
    }

}
