package dnt.itsnow.it;

import dnt.itsnow.model.ClientHostType;
import dnt.itsnow.model.ClientItsnowHosts;
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
        ResponseEntity<ClientItsnowHosts[]> entities = withLoginUser(new Callback<ResponseEntity<ClientItsnowHosts[]>>() {
            @Override
            public ResponseEntity<ClientItsnowHosts[]> perform(HttpHeaders headers) {
                HttpEntity<ClientItsnowHosts[]> request = new HttpEntity<ClientItsnowHosts[]>(headers);
                return getForEntity("/admin/api/hosts", ClientItsnowHosts[].class, request);
            }
        });
        validateIndexHeader(entities.getHeaders());
        ClientItsnowHosts[] hosts = entities.getBody();
        Assert.assertTrue(hosts.length > 0);
    }

    @Test
    public void testShow() throws Exception {
        ClientItsnowHosts host = withLoginUser(new Callback<ClientItsnowHosts>() {
            @Override
            public ClientItsnowHosts perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return getForObject("/admin/api/hosts/{id}", ClientItsnowHosts.class, request, 1L);
            }
        });
        Assert.assertNotNull(host);
    }

    @Test
    public void testCreate() throws Exception {
        final ClientItsnowHosts creating = new ClientItsnowHosts();
        creating.setName("srv7");
        creating.setAddress("172.16.3.30");
        creating.setType(ClientHostType.APP);
        creating.setCapacity(5);
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "dev@dnt");
        properties.setProperty("msu.version", "0.2.2-SNAPSHOT");
        properties.setProperty("msp.version", "0.2.2-SNAPSHOT");
        creating.setConfiguration(properties);
        ClientItsnowHosts created = null;
        try {
            created = withLoginUser(new Callback<ClientItsnowHosts>() {
                @Override
                public ClientItsnowHosts perform(HttpHeaders headers) {
                    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
                    HttpEntity<ClientItsnowHosts> request = new HttpEntity<ClientItsnowHosts>(creating, headers);
                    return postForObject("/admin/api/hosts", request, ClientItsnowHosts.class);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(created);
    }
}
