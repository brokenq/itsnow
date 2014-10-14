package itsnow.dnt.web.controller;

import dnt.itsnow.model.Department;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.DepartmentService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.support.JsonSupport;
import itsnow.dnt.config.DepartmentsControllerConfig;
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

@ContextConfiguration(classes = DepartmentsControllerConfig.class)
public class DepartmentsControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    DepartmentService departmentService;

    Department department;

    List<Department> departments;

    @Before
    public void setup() {
        department = new Department();
        department.setSn("007");
        department.setName("小卖部");
        department.setPosition(12L);
        department.setDescription("It's test.");
        department.creating();
        department.updating();

        departments = new ArrayList<Department>();
        departments.add(department);

        reset(departmentService);
    }

    @Test
    public void testIndex() throws Exception {
        expect(departmentService.findAll(anyBoolean()))
                .andReturn(new ArrayList<Department>(departments));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/departments");
        request = decorate(request);

        replay(departmentService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        expect(departmentService.findBySn("001")).andReturn(department);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/departments/001");
        request = decorate(request);

        replay(departmentService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        expect(departmentService.create(anyObject(Department.class))).andReturn(department);
        replay(departmentService);

        MockHttpServletRequestBuilder request = post("/api/departments").content(requestJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        expect(departmentService.findBySn("001")).andReturn(department);
        expect(departmentService.update(anyObject(Department.class))).andReturn(department);
        replay(departmentService);

        MockHttpServletRequestBuilder request = put("/api/departments/001").content(requestJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testDestroy() throws Exception {
        expect(departmentService.findBySn("001")).andReturn(department);
        departmentService.destroy(anyObject(Department.class));
        expectLastCall().once();

        replay(departmentService);

        URI uri = new URI("/api/departments/001");

        MockHttpServletRequestBuilder request = delete(uri);
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());
    }

    protected String requestJson(){
        return JsonSupport.toJSONString(department);
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(departmentService);
    }

}
