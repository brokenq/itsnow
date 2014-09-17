package itsnow.dnt.web.controller;

import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.WorkTimeService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.support.JsonSupport;
import itsnow.dnt.config.WorkTimesControllerConfig;
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

@ContextConfiguration(classes = WorkTimesControllerConfig.class)
public class WorkTimesControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    WorkTimeService workTimeSerivce;

    WorkTime workTime;

    List<WorkTime> workTimes;

    @Before
    public void setup() {

        workTime = new WorkTime();
        workTime.setId(11L);
        workTime.setSn("plan11");
        workTime.setName("工作日计划十一");
        workTime.setWorkDays("1,2,4,5,6");
        workTime.setStartAt("9:00");
        workTime.setEndAt("17:30");
        workTime.setDescription("This is a test.");
        workTime.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        workTime.setUpdatedAt(workTime.getCreatedAt());

        workTimes = new ArrayList<WorkTime>();
        workTimes.add(workTime);

        reset(workTimeSerivce);
    }

    @Test
    public void testIndex() throws Exception {

        expect(workTimeSerivce.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<WorkTime>(workTimes));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/work-times");
        request = decorate(request);

        replay(workTimeSerivce);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testShow() throws Exception {

        expect(workTimeSerivce.findBySn("plan11")).andReturn(workTime);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/work-times/plan11");
        request = decorate(request);

        replay(workTimeSerivce);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testUpdate() throws Exception {
        expect(workTimeSerivce.findBySn("plan11")).andReturn(workTime);
        expect(workTimeSerivce.update(anyObject(WorkTime.class))).andReturn(workTime);
        replay(workTimeSerivce);

        MockHttpServletRequestBuilder request = put("/api/work-times/plan11").content(accountJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testDestroy() throws Exception {
        expect(workTimeSerivce.findBySn("plan11")).andReturn(workTime);
        workTimeSerivce.destroy(anyString());
        expectLastCall().once();

        replay(workTimeSerivce);

        URI uri = new URI("/api/work-times/plan11");

        MockHttpServletRequestBuilder request = delete(uri);
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());

    }

    protected String accountJson(){
        return JsonSupport.toJSONString(workTime);
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(workTimeSerivce);
    }

}
