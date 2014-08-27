/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.config.MutableAccountsControllerConfig;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.MsuAccount;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.MutableAccountService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.support.JsonSupport;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h1>针对 Mutable Accounts Controller 的测试 </h1>
 */
@ContextConfiguration(classes = MutableAccountsControllerConfig.class)
public class MutableAccountsControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;
    @Autowired
    MutableAccountService accountService;

    private Account account;
    private List<Account> accounts;

    @Before
    public void setup() {

        this.account = new MsuAccount();
        this.account.setName("demo");
        this.account.setSn("msu-099");
        this.account.setId(1L);
        this.account.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.account.setUpdatedAt(this.account.getCreatedAt());

        this.accounts = new ArrayList<Account>();
        this.accounts.add(account);


        reset(accountService);
    }

    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(accountService);
    }

    @Test
    public void testIndex() throws Exception {
        // Service Mock 记录阶段
        expect(accountService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<Account>(accounts));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/admin/api/accounts");
        decorate(request);

        // Mock 准备播放
        replay(accountService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        expect(accountService.findBySn("demo")).andReturn(account);
        replay(accountService);

        MockHttpServletRequestBuilder request = get("/admin/api/accounts/demo");
        decorate(request);

        ResultActions result = this.browser.perform(request);

        decorate(result).andExpect(status().isOk());
    }


    @Test
    public void testUpdate() throws Exception {
        expect(accountService.findBySn("demo")).andReturn(account);
        expect(accountService.update(anyObject(Account.class))).andReturn(account);
        replay(accountService);

        MockHttpServletRequestBuilder request = put("/admin/api/accounts/demo").content(accountJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testDestroy() throws Exception {
        expect(accountService.findBySn("demo")).andReturn(account);
        accountService.delete(anyObject(Account.class));
        expectLastCall().once();

        replay(accountService);

        MockHttpServletRequestBuilder request = delete("/admin/api/accounts/demo");
        decorate(request);
        this.browser.perform(request).andExpect(status().isOk());

    }

    protected String accountJson(){
        return JsonSupport.toJSONString(account);
    }

}
