/**
 * Developer: Kadvin Date: 14-9-15 下午3:50
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.config.DeployControllerConfig;
import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.ItsnowProcessService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.itsnow.util.DeployFixture;
import dnt.support.JsonSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.LinkedList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Itsnow Processes Controller Test
 */
@ContextConfiguration(classes = DeployControllerConfig.class)
public class ItsnowProcessesControllerTest extends SessionSupportedControllerTest {
    @Autowired
    ItsnowProcessService      mockedService;

    ItsnowProcess    process;
    List<ItsnowProcess> processes;

    @Before
    public void setUp() throws Exception {
        process = DeployFixture.testProcess();
        processes = new LinkedList<ItsnowProcess>();
        processes.add(process);
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
                .andReturn(new DefaultPage<ItsnowProcess>(processes));
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/admin/api/processes");
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
    public void testShow() throws Exception {
        // Service Mock 记录阶段
        expect(mockedService.findByName(process.getName())).andReturn(process);
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/admin/api/processes/" + process.getName());
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
        expect(mockedService.create(isA(ItsnowProcess.class))).andReturn(process);
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = post("/admin/api/processes/");
        decorate(request).content(JsonSupport.toJSONString(process));

        // Mock 准备播放
        replay(mockedService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testDestroy() throws Exception {
        // Service Mock 记录阶段
        expect(mockedService.findByName(process.getName())).andReturn(process);
        mockedService.delete(process);
        expectLastCall().once();
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = delete("/admin/api/processes/" + process.getName());
        decorate(request);

        // Mock 准备播放
        replay(mockedService);

        // 执行
        this.browser.perform(request);

        // 对业务结果的验证
        status().isOk();
    }

    @Test
    public void testStart() throws Exception {
        // Service Mock 记录阶段
        expect(mockedService.findByName(process.getName())).andReturn(process);
        expect(mockedService.start(process)).andReturn("start-job-id");
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = put("/admin/api/processes/" + process.getName() + "/start");
        decorate(request);

        // Mock 准备播放
        replay(mockedService);

        // 执行
        ResultActions result = this.browser.perform(request);
        // 对业务结果的验证
        result.andExpect(status().isOk())
              .andExpect(content().string("start-job-id"));
    }

    @Test
    public void testStop() throws Exception {
        // Service Mock 记录阶段
        expect(mockedService.findByName(process.getName())).andReturn(process);
        expect(mockedService.stop(process)).andReturn("stop-job-id");
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = put("/admin/api/processes/" + process.getName() + "/stop");
        decorate(request);

        // Mock 准备播放
        replay(mockedService);

        // 执行
        ResultActions result = this.browser.perform(request);
        // 对业务结果的验证
        result.andExpect(status().isOk())
              .andExpect(content().string("stop-job-id"));

    }

    @Test
    public void testCancel() throws Exception {
        // Service Mock 记录阶段
        expect(mockedService.findByName(process.getName())).andReturn(process);
        mockedService.cancel(process, "starting-job-id");
        expectLastCall().once();
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = put("/admin/api/processes/" + process.getName() + "/cancel/starting-job-id");
        decorate(request);

        // Mock 准备播放
        replay(mockedService);

        // 执行
        ResultActions result = this.browser.perform(request);
        // 对业务结果的验证
        result.andExpect(status().isOk());

    }
}
