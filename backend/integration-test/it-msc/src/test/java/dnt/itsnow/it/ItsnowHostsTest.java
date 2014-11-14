package dnt.itsnow.it;

import dnt.itsnow.model.ClientHostStatus;
import dnt.itsnow.model.ClientItsnowHost;
import dnt.itsnow.util.DeployFixture;
import dnt.itsnow.util.ShareDatas;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * 测试 hosts 管理
 */
public class ItsnowHostsTest extends AbstractTest {

    private static ItsnowHostsTest test;

    public ClientItsnowHost create() throws Exception {
        final ClientItsnowHost creating = DeployFixture.testHost();
        try {
            return withLoginUser(new Callback<ClientItsnowHost>() {
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
        return null;
    }

    public ClientItsnowHost show(final ClientItsnowHost host) throws Exception {
        try {
            return withLoginUser(new Callback<ClientItsnowHost>() {
                @Override
                public ClientItsnowHost perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    return getForObject("/admin/api/hosts/{id}", ClientItsnowHost.class, request, host.getId());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void destroy(final ClientItsnowHost host) throws Exception {
        try {
            withLoginUser(new Job() {
                @Override
                public void perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    delete("/admin/api/hosts/{id}", request, host.getId());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientItsnowHost waitHostCreation(final ClientItsnowHost host) throws Exception {
        ClientItsnowHost created = null;
        try {
            created = withLoginUser(new Callback<ClientItsnowHost>() {
                @Override
                public ClientItsnowHost perform(HttpHeaders headers) {
                    HttpEntity<ClientItsnowHost> request = new HttpEntity<ClientItsnowHost>(headers);
                    String job = host.getConfiguration().getProperty("createInvocationId");
                    return getForObject("/admin/api/hosts/{id}/wait?job=" + job, ClientItsnowHost.class, request, host.getId());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("waitHostCreation" + created.toString());
        return created;
    }

    @Test
    public void testShow() throws Exception {
        ClientItsnowHost showing = show(ShareDatas.host);
        Assert.assertTrue(showing.getStatus() == ClientHostStatus.Running);
    }

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

}
