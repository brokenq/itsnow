package dnt.itsnow.web.controller;

import dnt.itsnow.config.ProcessDictionariesControllerConfig;
import dnt.itsnow.model.MenuItem;
import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.platform.util.Sort;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.MenuItemService;
import dnt.itsnow.service.ProcessDictionarySerivce;
import dnt.itsnow.test.controller.ApplicationControllerTest;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.support.JsonSupport;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Sin on 2014/8/26.
 */
@ContextConfiguration(classes = ProcessDictionariesControllerConfig.class)
public class ProcessDictionariesControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    ProcessDictionarySerivce processDictionaryService;

    ProcessDictionary dictionary;

    List<ProcessDictionary> dictionaries;

    @Before
    public void setup() {

        dictionary = new ProcessDictionary();
        dictionary.setCode("inc003");
        dictionary.setName("影响范围");
        dictionary.setLevel("high");
        dictionary.setLevelName("高");
        dictionary.setState("1");
        dictionary.setDescription("This is a test.");
        dictionary.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        dictionary.setUpdatedAt(dictionary.getCreatedAt());

        dictionaries = new ArrayList<ProcessDictionary>();
        dictionaries.add(dictionary);

        reset(processDictionaryService);
    }

    @Test
    public void testIndex() throws Exception {

        expect(processDictionaryService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<ProcessDictionary>(dictionaries));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/process-dictionaries");
        request = decorate(request);

        replay(processDictionaryService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testShow() throws Exception {

        expect(processDictionaryService.findBySn("inc003")).andReturn(dictionary);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/process-dictionaries/inc003");
        request = decorate(request);

        replay(processDictionaryService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testUpdate() throws Exception {
        expect(processDictionaryService.findBySn("inc003")).andReturn(dictionary);
        expect(processDictionaryService.update(anyObject(ProcessDictionary.class))).andReturn(dictionary);
        replay(processDictionaryService);

        MockHttpServletRequestBuilder request = put("/api/process-dictionaries/inc003").content(accountJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testDestroy() throws Exception {
        expect(processDictionaryService.findBySn("inc003")).andReturn(dictionary);
        processDictionaryService.destroy(anyString());
        expectLastCall().once();

        replay(processDictionaryService);

        URI uri = new URI("/api/process-dictionaries/inc003");

        MockHttpServletRequestBuilder request = delete(uri);
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());

    }

    protected String accountJson(){
        return JsonSupport.toJSONString(dictionary);
    }

    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(processDictionaryService);
    }

}
