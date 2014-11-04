package dnt.itsnow.it;
/**
 * Developer: Kadvin Date: 14-9-15 下午12:11
 */

import dnt.itsnow.model.ClientItsnowHost;
import dnt.itsnow.model.ClientItsnowProcess;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * 测试 process 管理
 */
@Ignore
public class ItsnowProcessesTest extends AbstractTest {

    @Test
    public void testIndex() throws Exception {
        ResponseEntity<ClientItsnowProcess[]> entities = withLoginUser(new Callback<ResponseEntity<ClientItsnowProcess[]>>() {
            @Override
            public ResponseEntity<ClientItsnowProcess[]> perform(HttpHeaders headers) {
                HttpEntity<ClientItsnowProcess[]> request = new HttpEntity<ClientItsnowProcess[]>(headers);
                return getForEntity("/admin/api/processes", ClientItsnowProcess[].class, request);
            }
        });
        validateIndexHeader(entities.getHeaders());
        ClientItsnowProcess[] processes = entities.getBody();
        Assert.assertTrue(processes.length > 0);//msc process at least
    }

    @Test
    public void testShow() throws Exception {
        ClientItsnowProcess process = withLoginUser(new Callback<ClientItsnowProcess>() {
            @Override
            public ClientItsnowProcess perform(HttpHeaders headers) {
                HttpEntity entity = new HttpEntity(headers);
                return getForObject("/admin/api/processes/{name}", ClientItsnowProcess.class , entity, "itsnow-msc");
            }
        });
        Assert.assertNotNull(process);
    }

    @Test
    public void testCreate() throws Exception {
        final ClientItsnowProcess creating = withLoginUser(new Callback<ClientItsnowProcess>() {
            @Override
            public ClientItsnowProcess perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return getForObject("/admin/api/processes/auto_new/{accountSn}", ClientItsnowProcess.class, request, "msp_test");
            }
        });
        Assert.assertNotNull(creating);
        ClientItsnowHost host = withLoginUser(new Callback<ClientItsnowHost>() {
            @Override
            public ClientItsnowHost perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return getForObject("/admin/api/hosts/{id}", ClientItsnowHost.class, request, 15L);
            }
        });
        Assert.assertNotNull(host);
        creating.setHostId(host.getId());
        ClientItsnowProcess created = null;
        try {
            created = withLoginUser(new Callback<ClientItsnowProcess>() {
                @Override
                public ClientItsnowProcess perform(HttpHeaders headers) {
                    HttpEntity<ClientItsnowProcess> request = new HttpEntity<ClientItsnowProcess>(creating, headers);
                    return postForObject("/admin/api/processes", request, ClientItsnowProcess.class);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(created);
    }

    @Test
    public void testAutoNew() throws Exception {
        ClientItsnowProcess entity = withLoginUser(new Callback<ClientItsnowProcess>() {
            @Override
            public ClientItsnowProcess perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return getForObject("/admin/api/processes/auto_new/{accountSn}", ClientItsnowProcess.class, request, "msc");
            }
        });
        Assert.assertNotNull(entity);
    }

    @Test
    public void testDestroy() throws Exception {
        withLoginUser(new Job() {
            @Override
            public void perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                delete("/admin/api/processes/{name}", request, "itsnow-test1");
            }
        });
    }

    @Test
    public void testStart() throws Exception {
        withLoginUser(new Job() {
            @Override
            public void perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                put("/admin/api/processes/{name}/start", request, "itsnow_msp_test");
            }
        });
    }

    @Test
    public void testStop() throws Exception {
        withLoginUser(new Job() {
            @Override
            public void perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                put("/admin/api/processes/{name}/stop", request, "itsnow_msc_test");
            }
        });
    }

    @Test
    public void testCancel() throws Exception {
        withLoginUser(new Job() {
            @Override
            public void perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                put("/admin/api/processes/{name}/cancel", request, "itsnow-test4");
            }
        });

    }
}
