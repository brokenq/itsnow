package dnt.itsnow.web.controller;

import dnt.itsnow.config.CommonMenuItemControllerConfig;
import dnt.itsnow.service.CommonMenuItemService;
import dnt.itsnow.test.controller.ApplicationControllerTest;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Sin on 2014/8/26.
 */
@ContextConfiguration(classes = CommonMenuItemControllerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonMenuItemControllerTest extends ApplicationControllerTest {

    @Autowired
    CommonMenuItemService commonMenuItemService;

    @Test
    public void testShow() throws Exception {
        // Service Mock 记录阶段
        expect(commonMenuItemService.findAll());

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/menu");
        decorate(request);

        // Mock 准备播放
        replay(commonMenuItemService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(commonMenuItemService);
    }

}
