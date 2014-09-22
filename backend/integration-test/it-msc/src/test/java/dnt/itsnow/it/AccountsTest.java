package dnt.itsnow.it; /**
 * Developer: Kadvin Date: 14-9-15 下午12:14
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import dnt.itsnow.model.ClientAccount;
import dnt.itsnow.model.ClientAccountRegistration;
import dnt.itsnow.model.ClientUser;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * 测试MSC帐户管理功能
 */
public class AccountsTest extends AbstractTest {


    @Test
    public void testIndex() throws Exception {
        ResponseEntity<ClientAccount[]> entities = withLoginUser(new Callback<ResponseEntity<ClientAccount[]>>() {
            @Override
            public ResponseEntity<ClientAccount[]> perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return getForEntity("/admin/api/accounts", ClientAccount[].class, request);
            }
        });
        ClientAccount[] clientAccounts = entities.getBody();
        Assert.assertTrue(clientAccounts.length > 2);
        validateIndexHeader(entities.getHeaders());
    }

    @Test
    public void testShow() throws Exception {
        ClientAccount mscAccount = withLoginUser(new Callback<ClientAccount>() {
            @Override
            public ClientAccount perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return getForObject("/admin/api/accounts/{sn}", ClientAccount.class, request, "msc");
            }
        });
        Assert.assertNotNull(mscAccount);
    }

    @Test
    public void testSignup() throws Exception {
        final ClientAccountRegistration registration = new ClientAccountRegistration();
        registration.setType("Enterprise");
        registration.setAsUser(true);

        ClientAccount account = new ClientAccount();
        account.setName("it-account");
        account.setDomain("it-domain");
        account.setType("base");
        registration.setAccount(account);

        ClientUser user = new ClientUser();
        user.setName("it-user");
        user.setEmail("test@it.com");
        user.setPhone("12345678901");
        user.setPassword("123456");
        user.setRepeatPassword("123456");
        registration.setUser(user);

        String json = new ObjectMapper().writeValueAsString(registration);
        System.out.println(json);

        ClientAccount createdAccount = withCsrf(new Callback<ClientAccount>() {
            @Override
            public ClientAccount perform(HttpHeaders headers) {
                HttpEntity<ClientAccountRegistration> request = new HttpEntity<ClientAccountRegistration>(registration, headers);
                return postForObject("/public/accounts", request, ClientAccount.class);
            }
        });
        Assert.assertNotNull(createdAccount);
    }

}
