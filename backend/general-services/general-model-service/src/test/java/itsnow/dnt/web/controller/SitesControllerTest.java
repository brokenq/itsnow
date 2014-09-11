package itsnow.dnt.web.controller;

import dnt.itsnow.model.Department;
import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.model.Site;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.SiteService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.support.JsonSupport;
import itsnow.dnt.config.SitesControllerConfig;
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

@ContextConfiguration(classes = SitesControllerConfig.class)
public class SitesControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    SiteService siteService;

    Site site;

    List<Site> sites;

    @Before
    public void setup() {

        Department department = new Department();
        department.setId(1L);
        department.setSn("007");
        department.setName("小卖部");
        department.setDescription("It's test.");
        department.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        department.setUpdatedAt(department.getCreatedAt());

        List<Department> departments = new ArrayList<Department>();
        departments.add(department);

        site = new Site();
        site.setId(1L);
        site.setSn("001");
        site.setName("大众五厂");
        site.setAddress("待定");
        ProcessDictionary dictionary = new ProcessDictionary();
        dictionary.setId(1L);
        site.setProcessDictionary(dictionary);
        WorkTime workTime = new WorkTime();
        workTime.setId(1L);
        site.setWorkTime(workTime);
        site.setDescription("It's test.");
        site.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        site.setUpdatedAt(site.getCreatedAt());
        site.setDepartments(departments);

        sites = new ArrayList<Site>();
        sites.add(site);



        reset(siteService);
    }

    @Test
    public void testIndex() throws Exception {
        expect(siteService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<Site>(sites));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/sites");
        request = decorate(request);

        replay(siteService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        expect(siteService.findBySn("001")).andReturn(site);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/sites/001");
        request = decorate(request);

        replay(siteService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        expect(siteService.create(anyObject(Site.class))).andReturn(site);
        replay(siteService);

        MockHttpServletRequestBuilder request = post("/api/sites").content(requestJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        expect(siteService.findBySn("001")).andReturn(site);
        expect(siteService.update(anyObject(Site.class))).andReturn(site);
        replay(siteService);

        MockHttpServletRequestBuilder request = put("/api/sites/001").content(requestJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testDestroy() throws Exception {
        expect(siteService.findBySn("001")).andReturn(site);
        expect(siteService.destroy(anyObject(Site.class))).andReturn(site);
        expectLastCall().once();

        replay(siteService);

        URI uri = new URI("/api/sites/001");

        MockHttpServletRequestBuilder request = delete(uri);
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());
    }

    protected String requestJson(){
        return JsonSupport.toJSONString(site);
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(siteService);
    }

}
