package dnt.itsnow.web.controller;

import dnt.itsnow.config.DictionariesControllerConfig;
import dnt.itsnow.model.DictDetail;
import dnt.itsnow.model.Dictionary;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.DictionaryService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Sin on 2014/8/26.
 */
@ContextConfiguration(classes = DictionariesControllerConfig.class)
public class DictionariesControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;

    @Autowired
    DictionaryService dictionaryService;

    Dictionary dictionary;

    List<Dictionary> dictionaries;

    @Before
    public void setup() {

        dictionary = new Dictionary();

        DictDetail detail = new DictDetail();
        detail.setKey("key");
        detail.setValue("value");
        DictDetail[] detailList = new DictDetail[]{detail};

        dictionary.setId(3L);
        dictionary.setCode("inc003");
        dictionary.setName("影响范围");
        dictionary.setLabel("北京");
        dictionary.setDescription("This is a test.");
        dictionary.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        dictionary.setUpdatedAt(dictionary.getCreatedAt());
        dictionary.setDetails(detailList);

        dictionaries = new ArrayList<Dictionary>();
        dictionaries.add(dictionary);

        reset(dictionaryService);
    }

    @Test
    public void testCreate() throws Exception {
        expect(dictionaryService.create(anyObject(Dictionary.class))).andReturn(dictionary);
        replay(dictionaryService);

        MockHttpServletRequestBuilder request = post("/api/dictionaries").content(accountJson());

        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testIndex() throws Exception {

        expect(dictionaryService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<Dictionary>(dictionaries));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/dictionaries");
        request = decorate(request);

        replay(dictionaryService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testShow() throws Exception {

        expect(dictionaryService.findByCode("003")).andReturn(dictionary);

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/api/dictionaries/003");
        request = decorate(request);

        replay(dictionaryService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

//    @Test
//    public void testList() throws Exception {
//
//        expect(dictionaryService.findByCode("inc003")).andReturn(dictionaries);
//
//        // 准备 Mock Request
//        MockHttpServletRequestBuilder request = get("/api/dictionaries/code/inc003");
//        request = decorate(request);
//
//        replay(dictionaryService);
//
//        // 执行
//        ResultActions result = this.browser.perform(request);
//
//        // 对业务结果的验证
//        decorate(result).andExpect(status().isOk());
//
//    }

    @Test
    public void testUpdate() throws Exception {
        expect(dictionaryService.findByCode("001")).andReturn(dictionary);
        expect(dictionaryService.update(anyObject(Dictionary.class))).andReturn(dictionary);
        replay(dictionaryService);

        MockHttpServletRequestBuilder request = put("/api/dictionaries/001").content(accountJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testDestroy() throws Exception {
        expect(dictionaryService.findByCode("003")).andReturn(dictionary);
        dictionaryService.destroy(anyObject(Dictionary.class));
        expectLastCall().once();

        replay(dictionaryService);

        URI uri = new URI("/api/dictionaries/003");

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
        verify(dictionaryService);
    }

}
