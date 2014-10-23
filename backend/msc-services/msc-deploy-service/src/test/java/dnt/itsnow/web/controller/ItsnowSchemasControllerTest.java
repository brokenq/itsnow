package dnt.itsnow.web.controller;

import dnt.itsnow.config.DeployControllerConfig;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.ItsnowSchemaService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Itsnow hosts controller test
 */
@ContextConfiguration(classes = DeployControllerConfig.class)
public class ItsnowSchemasControllerTest extends SessionSupportedControllerTest {
    @Autowired
    ItsnowSchemaService mockedService;

    ItsnowSchema schema;
    List<ItsnowSchema> schemas;

    @Before
    public void setUp() throws Exception {
        schema = DeployFixture.testSchema();
        schemas = new LinkedList<ItsnowSchema>();
        schemas.add(schema);

        resetAll();
    }

    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void testIndex() throws Exception {
        // Service Mock 记录阶段
        expect(mockedService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<ItsnowSchema>(schemas));
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/admin/api/schemas");
        decorate(request);

        // Mock 准备播放
        replayAll();

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
    public void testCreate() throws Exception {
        // Service Mock 记录阶段
        expect(mockedService.create(isA(ItsnowSchema.class))).andReturn(schema);
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = post("/admin/api/schemas");
        decorate(request).content(JsonSupport.toJSONString(schema));

        // Mock 准备播放
        replayAll();

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testDestroy() throws Exception {
        schema.setId(102L);
        // Service Mock 记录阶段
        expect(mockedService.findById(schema.getId())).andReturn(schema);
        mockedService.delete(schema);
        expectLastCall().once();
        // 准备 Mock Request
        MockHttpServletRequestBuilder request = delete("/admin/api/schemas/" + schema.getId());
        decorate(request);

        // Mock 准备播放
        replayAll();

        // 执行
        this.browser.perform(request);

        // 对业务结果的验证
        status().isOk();
    }

    void resetAll(){
        reset(mockedService);
    }

    void verifyAll(){
        verify(mockedService);
    }

    void replayAll(){
        replay(mockedService);
    }
}
