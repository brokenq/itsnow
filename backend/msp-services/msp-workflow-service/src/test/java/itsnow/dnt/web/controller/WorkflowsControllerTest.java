package itsnow.dnt.web.controller;

import dnt.itsnow.model.*;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.MspWorkflowService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.support.JsonSupport;
import itsnow.dnt.config.MspWorkflowsControllerConfig;
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

@ContextConfiguration(classes = MspWorkflowsControllerConfig.class)
public class WorkflowsControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    MspWorkflowService mspWorkflowService;

    Workflow workflow;

    List<Workflow> workflows;

    @Before
    public void setup() {

        workflow = new Workflow();
        workflow.setName("工作流程一");
        workflow.setDescription("It's test.");
        workflow.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        workflow.setUpdatedAt(workflow.getCreatedAt());

        ProcessDictionary dictionary = new ProcessDictionary();
        dictionary.setId(1L);
        workflow.setProcessDictionary(dictionary);

        ActReProcdef actReProcdef = new ActReProcdef();
        actReProcdef.setId_("1");
        workflow.setActReProcdef(actReProcdef);

        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setId(1L);

        workflows = new ArrayList<Workflow>();
        workflows.add(workflow);

        reset(mspWorkflowService);
    }

    @Test
    public void testIndex() throws Exception {
        expect(mspWorkflowService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<Workflow>(workflows));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/mspWorkflows");
        request = decorate(request);

        replay(mspWorkflowService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        expect(mspWorkflowService.findBySn("001")).andReturn(workflow);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/mspWorkflows/001");
        request = decorate(request);

        replay(mspWorkflowService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        expect(mspWorkflowService.create(anyObject(Workflow.class))).andReturn(workflow);
        replay(mspWorkflowService);

        MockHttpServletRequestBuilder request = post("/api/mspWorkflows").content(requestJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        expect(mspWorkflowService.findBySn("001")).andReturn(workflow);
        expect(mspWorkflowService.update(anyObject(Workflow.class))).andReturn(workflow);
        replay(mspWorkflowService);

        MockHttpServletRequestBuilder request = put("/api/mspWorkflows/001").content(requestJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testDestroy() throws Exception {
        expect(mspWorkflowService.findBySn("001")).andReturn(workflow);
        expect(mspWorkflowService.destroy(anyObject(Workflow.class))).andReturn(workflow);
        expectLastCall().once();

        replay(mspWorkflowService);

        URI uri = new URI("/api/mspWorkflows/001");

        MockHttpServletRequestBuilder request = delete(uri);
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());
    }

    protected String requestJson(){
        return JsonSupport.toJSONString(workflow);
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(mspWorkflowService);
    }

}
