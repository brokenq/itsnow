package itsnow.dnt.web.controller;

import dnt.itsnow.model.ActReProcdef;
import dnt.itsnow.model.Dictionary;
import dnt.itsnow.model.ServiceItem;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.WorkflowService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.support.JsonSupport;
import itsnow.dnt.config.MsuWorkflowsControllerConfig;
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

@ContextConfiguration(classes = MsuWorkflowsControllerConfig.class)
public class MsuWorkflowsControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    WorkflowService workflowService;

    Workflow workflow;

    List<Workflow> workflows;

    @Before
    public void setup() {

        workflow = new Workflow();
        workflow.setName("工作流程一");
        workflow.setDescription("It's test.");
        workflow.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        workflow.setUpdatedAt(workflow.getCreatedAt());

        Dictionary dictionary = new Dictionary();
        dictionary.setId(1L);
        workflow.setDictionary(dictionary);

        ActReProcdef actReProcdef = new ActReProcdef();
        actReProcdef.setId_("1");
        workflow.setActReProcdef(actReProcdef);

        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setId(1L);

        workflows = new ArrayList<Workflow>();
        workflows.add(workflow);

        reset(workflowService);
    }

    @Test
    public void testIndex() throws Exception {
        expect(workflowService.findAll(anyString(), isA(PageRequest.class), anyString()))
                .andReturn(new DefaultPage<Workflow>(workflows));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/msu_workflows");
        request = decorate(request);

        replay(workflowService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        expect(workflowService.findBySn("001", Workflow.PRIVATE_SERVICE_ITEM)).andReturn(workflow);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/msu_workflows/001");
        request = decorate(request);

        replay(workflowService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        expect(workflowService.create(anyObject(Workflow.class))).andReturn(workflow);
        replay(workflowService);

        MockHttpServletRequestBuilder request = post("/api/msu_workflows").content(requestJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        expect(workflowService.findBySn("001", Workflow.PRIVATE_SERVICE_ITEM)).andReturn(workflow);
        expect(workflowService.update(anyObject(Workflow.class))).andReturn(workflow);
        replay(workflowService);

        MockHttpServletRequestBuilder request = put("/api/msu_workflows/001").content(requestJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testDestroy() throws Exception {
        expect(workflowService.findBySn("001", Workflow.PRIVATE_SERVICE_ITEM)).andReturn(workflow);
        workflowService.destroy(anyObject(Workflow.class));
        expectLastCall().once();

        replay(workflowService);

        URI uri = new URI("/api/msu_workflows/001");

        MockHttpServletRequestBuilder request = delete(uri);
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());
    }

    protected String requestJson() {
        return JsonSupport.toJSONString(workflow);
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(workflowService);
    }

}
