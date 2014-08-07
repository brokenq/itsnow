/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.config.MutableAccountsControllerConfig;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.MsuAccount;
import dnt.support.JsonSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h1>针对 AccountsController 的测试 </h1>
 */
@ContextConfiguration(classes = MutableAccountsControllerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MutableAccountsControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private Account account;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.account = new MsuAccount();
    }

    @Test
    public void testIndex() throws Exception {
        this.mockMvc.perform(get("/admin/api/accounts"))
                .andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        this.mockMvc.perform(get("/admin/api/accounts/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("demo"));
    }

    @Test
    public void testCreate() throws Exception {
        this.mockMvc.perform(post("/admin/api/accounts").content(accountJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("demo"));

    }

    @Test
    public void testUpdate() throws Exception {
        this.mockMvc.perform(put("/admin/api/accounts/demo").content(accountJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("demo"));

    }

    @Test
    public void testDestroy() throws Exception {
        this.mockMvc.perform(delete("/admin/api/accounts/demo"))
                .andExpect(status().isOk());

    }

    protected String accountJson(){
        return JsonSupport.toJSONString(account);
    }
}
