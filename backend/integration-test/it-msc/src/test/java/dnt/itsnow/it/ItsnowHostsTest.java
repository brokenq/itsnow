package dnt.itsnow.it;

import dnt.itsnow.model.ClientHostType;
import dnt.itsnow.model.ClientItsnowHost;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Properties;

/**
 * 测试 hosts 管理
 */
@Ignore
public class ItsnowHostsTest extends AbstractTest {

    @Test
    public void testIndex() throws Exception {
        ResponseEntity<ClientItsnowHost[]> entities = withLoginUser(new Callback<ResponseEntity<ClientItsnowHost[]>>() {
            @Override
            public ResponseEntity<ClientItsnowHost[]> perform(HttpHeaders headers) {
                HttpEntity<ClientItsnowHost[]> request = new HttpEntity<ClientItsnowHost[]>(headers);
                return getForEntity("/admin/api/hosts", ClientItsnowHost[].class, request);
            }
        });
        validateIndexHeader(entities.getHeaders());
        ClientItsnowHost[] hosts = entities.getBody();
        Assert.assertTrue(hosts.length > 0);
    }

    @Test
    public void testShow() throws Exception {
        ClientItsnowHost host = withLoginUser(new Callback<ClientItsnowHost>() {
            @Override
            public ClientItsnowHost perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return getForObject("/admin/api/hosts/{id}", ClientItsnowHost.class, request, 1L);
            }
        });
        Assert.assertNotNull(host);
    }

    @Test
    public void testCreate() throws Exception {
        final ClientItsnowHost creating = new ClientItsnowHost();
        creating.setName("srv7.itsnow.com");
        creating.setAddress("172.16.3.30");
        creating.setType(ClientHostType.COM);
        creating.setCapacity(5);
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "dev@dnt");
        properties.setProperty("msu.version", "0.2.2-SNAPSHOT");
        properties.setProperty("msp.version", "0.2.2-SNAPSHOT");
        creating.setConfiguration(properties);
        ClientItsnowHost created = null;
        try {
            created = withLoginUser(new Callback<ClientItsnowHost>() {
                @Override
                public ClientItsnowHost perform(HttpHeaders headers) {
                    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
                    HttpEntity<ClientItsnowHost> request = new HttpEntity<ClientItsnowHost>(creating, headers);
                    return postForObject("/admin/api/hosts", request, ClientItsnowHost.class);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(created);
    }

    @Test
    public void testDestroy() throws Exception {
        try {
            withLoginUser(new Job() {
                @Override
                public void perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    delete("/admin/api/hosts/{id}", request, 6L);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
