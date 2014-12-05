package dnt.itsnow.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import dnt.itsnow.model.ClientAccount;
import dnt.itsnow.model.ClientAccountRegistration;
import dnt.itsnow.util.DeployFixture;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * 测试MSC帐户管理功能
 */
@Ignore
public class AccountsTest extends AbstractTest {

    private static AccountsTest test;

    public ClientAccount signUp() throws Exception {
        final ClientAccountRegistration registration = DeployFixture.testRegistration();

        String json = new ObjectMapper().writeValueAsString(registration);
        System.out.println(json);

        try {
            return withCsrf(new Callback<ClientAccount>() {
                @Override
                public ClientAccount perform(HttpHeaders headers) {
                    HttpEntity<ClientAccountRegistration> request = new HttpEntity<ClientAccountRegistration>(registration, headers);
                    return postForObject("/public/accounts", request, ClientAccount.class);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClientAccount approve(final ClientAccount account) throws Exception {
        try {
            return withLoginUser(new Callback<ClientAccount>() {
                @Override
                public ClientAccount perform(HttpHeaders headers) {
                    HttpEntity<ClientAccount> request = new HttpEntity<ClientAccount>(headers);
                    return getForObject("/admin/api/accounts/{sn}/approve", ClientAccount.class, request, account.getSn());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void destroy(final ClientAccount account) throws Exception {
        try {
            withLoginUser(new Job() {
                @Override
                public void perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    delete("/admin/api/accounts/{sn}", request, account.getSn());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientAccount show(final ClientAccount account) throws Exception {
        try {
            return withLoginUser(new Callback<ClientAccount>() {
                @Override
                public ClientAccount perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    return getForObject("/admin/api/accounts/{sn}", ClientAccount.class, request, account.getSn());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resetNew(final ClientAccount account) throws Exception {
        try {
            withLoginUser(new Job() {
                @Override
                public void perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    put("/admin/api/accounts/{sn}/resetNew", request, account.getSn());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

}
