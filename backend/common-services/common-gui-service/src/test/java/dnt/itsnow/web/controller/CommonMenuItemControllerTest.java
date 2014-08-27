package dnt.itsnow.web.controller;

import dnt.itsnow.config.CommonMenuItemControllerConfig;
import dnt.itsnow.model.MenuItem;
import dnt.itsnow.service.CommonMenuItemService;
import dnt.itsnow.test.controller.ApplicationControllerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Sin on 2014/8/26.
 */
@ContextConfiguration(classes = CommonMenuItemControllerConfig.class)
public class CommonMenuItemControllerTest extends ApplicationControllerTest {

    @Autowired
    CommonMenuItemService commonMenuItemService;

    List<MenuItem> menuItemList;

    @Before
    public void setup() {

        menuItemList = new ArrayList<MenuItem>();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setParentId(null);
        menuItem.setName("用户");
        menuItem.setUrl("www.google.com");
        menuItemList.add(menuItem);

        reset(commonMenuItemService);
    }

    @Test
    public void testShow() throws Exception {

        expect(commonMenuItemService.findAll())
                .andReturn(menuItemList);

        replay(commonMenuItemService);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/menu");
        request = decorate(request);

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
