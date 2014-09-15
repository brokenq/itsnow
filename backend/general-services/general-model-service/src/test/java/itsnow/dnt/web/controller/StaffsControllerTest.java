package itsnow.dnt.web.controller;

import dnt.itsnow.model.*;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.StaffService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.support.JsonSupport;
import itsnow.dnt.config.StaffsControllerConfig;
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

@ContextConfiguration(classes = StaffsControllerConfig.class)
public class StaffsControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    StaffService staffService;

    Staff staff;

    List<Staff> staffs;

    @Before
    public void setup() {

        staff = new Staff();
        staff.setNo("010");
        staff.setName("王二麻子");
        staff.setMobilePhone("15901968888");
        staff.setFixedPhone("63557788");
        staff.setEmail("stone5751@126.com");
        staff.setTitle("攻城尸");
        staff.setType("合同工");
        staff.setStatus("1");
        staff.setDescription("This is a test.");
        staff.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        staff.setUpdatedAt(staff.getCreatedAt());

        Department department = new Department();
        department.setId(1L);
        department.setSn("005");
        department.setName("公关部");
        department.setDescription("It's test.");
        department.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        department.setUpdatedAt(department.getCreatedAt());
        staff.setDepartment(department);

        Site site = new Site();
        site.setId(1L);
        staff.setSite(site);

        User user = new User();
        user.setId(1L);
        staff.setUser(user);

        staffs = new ArrayList<Staff>();
        staffs.add(staff);

        reset(staffService);
    }

    @Test
    public void testIndex() throws Exception {
        expect(staffService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<Staff>(staffs));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/staffs");
        request = decorate(request);

        replay(staffService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        expect(staffService.findByNo("001")).andReturn(staff);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/staffs/001");
        request = decorate(request);

        replay(staffService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        expect(staffService.create(anyObject(Staff.class))).andReturn(staff);
        replay(staffService);

        MockHttpServletRequestBuilder request = post("/api/staffs").content(requestJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        expect(staffService.findByNo("001")).andReturn(staff);
        expect(staffService.update(anyObject(Staff.class))).andReturn(staff);
        replay(staffService);

        MockHttpServletRequestBuilder request = put("/api/staffs/001").content(requestJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testDestroy() throws Exception {
        expect(staffService.findByNo("001")).andReturn(staff);
        expect(staffService.destroy(anyObject(Staff.class))).andReturn(staff);
        expectLastCall().once();

        replay(staffService);

        URI uri = new URI("/api/staffs/001");

        MockHttpServletRequestBuilder request = delete(uri);
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());
    }

    protected String requestJson(){
        return JsonSupport.toJSONString(staff);
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(staffService);
    }

}
