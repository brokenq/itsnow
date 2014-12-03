package dnt.itsnow.web.controller;

import dnt.itsnow.config.RolesControllerConfig;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.Role;
import dnt.itsnow.model.User;
import net.happyonroad.platform.util.DefaultPage;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.RoleService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import net.happyonroad.support.JsonSupport;
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

    Role role;

    List<Role> roles;

    User user;

    List<User> users;

    @Before
    public void setup() {

        role = new Role();
        role.setId(1L);
        role.setName("ROLE_ADMIN");
        role.setDescription("This is a test.");
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());

        roles = new ArrayList<Role>();
        roles.add(role);

        user = new User();
        user.setName("USER_ACTION_TEST");
        user.setUsername("USER_ACTION_TEST");
        user.setEmail("stone@126.com");
        user.setPhone("15901968888");
        user.setPassword("Hi");
        user.setRepeatPassword("Hi");

        users = new ArrayList<User>();
        users.add(user);

        reset(roleService);
    }

    @Test
    public void testIndex() throws Exception {

        expect(roleService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<Role>(roles));

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

        expect(roleService.findByName(anyString())).andReturn(role);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/roles/ROLE_ADMIN");
        request = decorate(request);

        replay(roleService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testUpdate() throws Exception {
        expect(roleService.findByName("ROLE_ADMIN")).andReturn(role);
        expect(roleService.update(anyObject(Role.class))).andReturn(role);
        replay(roleService);

        MockHttpServletRequestBuilder request = put("/api/roles/ROLE_ADMIN").content(roleJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testDestroy() throws Exception {
        expect(roleService.findByName("ROLE_ADMIN")).andReturn(role);
        roleService.destroy(anyObject(Role.class));
        expectLastCall().once();

        replay(roleService);

        URI uri = new URI("/api/roles/ROLE_ADMIN");

        MockHttpServletRequestBuilder request = delete(uri);
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());

    }

    @Test
    public void testListUsers() throws Exception {

        expect(userService.findAllByAccountAndContract(anyObject(Account.class))).andReturn(users);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/roles/users/belongs_to_account");
        request = decorate(request);

        replay(roleService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        result.andExpect(status().isOk());

    }

    protected String roleJson(){
        return JsonSupport.toJSONString(role);
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(roleService);
    }

}
