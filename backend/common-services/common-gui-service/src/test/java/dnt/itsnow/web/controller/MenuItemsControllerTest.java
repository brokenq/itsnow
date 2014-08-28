package dnt.itsnow.web.controller;

import dnt.itsnow.config.MenuItemsControllerConfig;
import dnt.itsnow.model.MenuItem;
import dnt.itsnow.service.MenuItemService;
import dnt.itsnow.test.controller.ApplicationControllerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Sin on 2014/8/26.
 */
@ContextConfiguration(classes = MenuItemsControllerConfig.class)
public class MenuItemsControllerTest extends ApplicationControllerTest {

    @Autowired
    MenuItemService commonMenuItemService;

    List<MenuItem> menuItemList;

    MenuItem menuItem;

    @Before
    public void setup() {

        menuItemList = new ArrayList<MenuItem>();
        menuItem = new MenuItem();
        this.menuItem.setId(1L);
        this.menuItem.setName("用户");
        this.menuItem.setState("index.user");
        this.menuItem.setTemplateUrl("user/list-user.tpl.html");
        this.menuItem.setPosition(0L);
        this.menuItem.setShortcut("Shift+Ctrl+A");
        this.menuItem.setDescription("This is a test.");
        this.menuItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.menuItem.setUpdatedAt(this.menuItem.getCreatedAt());
        menuItemList.add(menuItem);

        reset(commonMenuItemService);
    }

    @Test
    public void testShow() throws Exception {

        expect(commonMenuItemService.findAll(true))
                .andReturn(menuItemList);

        replay(commonMenuItemService);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/menu_items");
        request = decorate(request);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testUpdate() throws Exception {

        expect(commonMenuItemService.findAll(true))
                .andReturn(menuItemList);

        replay(commonMenuItemService);

        // 准备 Mock Request
        menuItem.setName("企业用户");
        MockHttpServletRequestBuilder request = put("/api/menu_items", menuItem);
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
