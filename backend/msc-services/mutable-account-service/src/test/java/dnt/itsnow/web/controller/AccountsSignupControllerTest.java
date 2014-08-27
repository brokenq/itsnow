/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.config.MutableAccountsControllerConfig;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.MspAccount;
import dnt.itsnow.model.MsuAccount;
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
public class AccountsSignupControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService     userService;
    @Autowired
    MutableAccountService accountService;

    private MsuAccount    msuAccount;
    private MspAccount    mspAccount;

    @Before
    public void setup() {

        this.msuAccount = new MsuAccount();
        this.msuAccount.setName("User");
        this.msuAccount.setDomain("user");
        this.msuAccount.setSn("msu-099");
        this.msuAccount.setId(2L);
        this.msuAccount.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.msuAccount.setUpdatedAt(this.msuAccount.getCreatedAt());

        this.mspAccount = new MspAccount();
        this.mspAccount.setName("Provider");
        this.mspAccount.setDomain("provider");
        this.mspAccount.setSn("msp-099");
        this.mspAccount.setId(3L);
        this.mspAccount.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.mspAccount.setUpdatedAt(this.msuAccount.getCreatedAt());


        reset(accountService);
    }

    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(accountService);
    }

    @Test
    public void testMsuSignup() throws Exception {
        expect(accountService.create(anyObject(Account.class))).andReturn(msuAccount);
        replay(accountService);

        MockHttpServletRequestBuilder request = post("/api/msu/signup").content(msuJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    @Test
    public void testMspSignup() throws Exception {
        expect(accountService.create(anyObject(Account.class))).andReturn(mspAccount);
        replay(accountService);

        MockHttpServletRequestBuilder request = post("/api/msp/signup").content(mspJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    protected String msuJson(){
        return JsonSupport.toJSONString(msuAccount);
    }

    protected String mspJson(){
        return JsonSupport.toJSONString(mspAccount);
    }

}
