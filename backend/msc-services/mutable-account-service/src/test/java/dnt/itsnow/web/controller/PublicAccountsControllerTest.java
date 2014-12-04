/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.config.MutableAccountsControllerConfig;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.MutableAccountService;
import dnt.itsnow.test.controller.SessionSupportedControllerTest;
import dnt.itsnow.web.model.AccountRegistration;
import dnt.itsnow.web.model.RegistrationType;
import net.happyonroad.support.JsonSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h1>针对 Mutable Accounts Controller 的测试 </h1>
 */
@ContextConfiguration(classes = MutableAccountsControllerConfig.class)
public class PublicAccountsControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService     userService;
    @Autowired
    MutableAccountService accountService;

    AccountRegistration registration;

    @Before
    public void setup() {
        registration = new AccountRegistration();
        registration.setType(RegistrationType.Enterprise);
        registration.setAsProvider(true);

        Account account = new Account();
        account.setName("test-account");
        account.setDomain("test-domain");
        account.setDescription("test account");
        registration.setAccount(account);

        User user = new User();
        user.setUsername("jay.xiong");
        user.setPassword("123456");
        user.setRepeatPassword("123456");
        user.setPhone("138202020202");
        user.setEmail("jay@xiong.com");
        registration.setUser(user);

        reset(accountService);
    }

    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(accountService);
    }

    @Test
    public void testSignup() throws Exception {
        expect(accountService.register(anyObject(Account.class), anyObject(User.class)))
                .andReturn(registration.getAccount());
        replay(accountService);

        MockHttpServletRequestBuilder request = post("/public/accounts").content(registrationJson());
        decorate(request);

        ResultActions result = this.browser.perform(request);
        decorate(result).andExpect(status().isOk());

    }

    protected String registrationJson() {
        return JsonSupport.toJSONString(registration);
    }

}
