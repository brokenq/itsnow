package dnt.itsnow.web.controller;

import dnt.itsnow.config.GroupsControllerConfig;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.GroupService;
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

@Ignore
@ContextConfiguration(classes = GroupsControllerConfig.class)
public class GroupsControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    GroupService groupService;

    Group group;

    List<Group> groups;

    @Before
    public void setup() {

        group = new Group();
        group.setId(1L);
        group.setSn("010");
        group.setName("用户");
        group.setDescription("This is a test.");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        group.setUpdatedAt(group.getCreatedAt());

        groups = new ArrayList<Group>();
        groups.add(group);

        reset(groupService);
    }

    @Test
    public void testIndex() throws Exception {

//        expect(groupService.search(anyString())).andReturn(groups);

        expect(groupService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<Group>(groups));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/groups");
        request = decorate(request);

        replay(groupService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testShow() throws Exception {

        expect(groupService.find("user")).andReturn(group);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/groups/user");
        request = decorate(request);

        replay(groupService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testUpdate() throws Exception {
        expect(groupService.findBySn("user")).andReturn(group);
        expect(groupService.update(anyObject(Group.class))).andReturn(group);
        replay(groupService);

        MockHttpServletRequestBuilder request = put("/api/groups/user").content(accountJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testDestroy() throws Exception {
        expect(groupService.findBySn("user")).andReturn(group);
        expect(groupService.destroy(anyObject(Group.class))).andReturn(group);
        expectLastCall().once();

        replay(groupService);

        URI uri = new URI("/api/groups/user");

        MockHttpServletRequestBuilder request = delete(uri);
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());

    }

    protected String accountJson(){
        return JsonSupport.toJSONString(group);
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(groupService);
    }

}
