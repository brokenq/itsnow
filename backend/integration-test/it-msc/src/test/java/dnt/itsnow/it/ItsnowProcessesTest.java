package dnt.itsnow.it;
/**
 * Developer: Kadvin Date: 14-9-15 下午12:11
 */

import dnt.itsnow.model.ClientAccount;
import dnt.itsnow.model.ClientItsnowHost;
import dnt.itsnow.model.ClientItsnowProcess;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * 测试 process 管理
 */
public class ItsnowProcessesTest extends AbstractTest {

    private static ItsnowProcessesTest test;
    private AccountsTest accountsTest;
    private ItsnowHostsTest hostsTest;
    private ClientAccount account;
    private ClientItsnowHost host;

    public ClientItsnowProcess autoNew(final ClientAccount account) throws Exception {
        try {
            return withLoginUser(new Callback<ClientItsnowProcess>() {
                @Override
                public ClientItsnowProcess perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    return getForObject("/admin/api/processes/auto_new/{accountSn}", ClientItsnowProcess.class, request, account.getSn());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClientItsnowProcess create(final ClientItsnowProcess process) throws Exception {
        try {
            return withLoginUser(new Callback<ClientItsnowProcess>() {
                @Override
                public ClientItsnowProcess perform(HttpHeaders headers) {
                    HttpEntity<ClientItsnowProcess> request = new HttpEntity<ClientItsnowProcess>(process, headers);
                    return postForObject("/admin/api/processes", request, ClientItsnowProcess.class);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClientItsnowProcess show(final ClientItsnowProcess process) throws Exception {
        try {
            return withLoginUser(new Callback<ClientItsnowProcess>() {
                @Override
                public ClientItsnowProcess perform(HttpHeaders headers) {
                    HttpEntity entity = new HttpEntity(headers);
                    return getForObject("/admin/api/processes/{name}", ClientItsnowProcess.class , entity, process.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void start(final ClientItsnowProcess process) throws Exception {
        try {
            withLoginUser(new Job() {
                @Override
                public void perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    put("/admin/api/processes/{name}/start", request, process.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(final ClientItsnowProcess process) throws Exception {
        try {
            withLoginUser(new Job() {
                @Override
                public void perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    put("/admin/api/processes/{name}/stop", request, process.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel(final ClientItsnowProcess process) throws Exception {
        try {
            withLoginUser(new Job() {
                @Override
                public void perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    put("/admin/api/processes/{name}/cancel", request, process.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(final ClientItsnowProcess process) throws Exception {
        try {
            withLoginUser(new Job() {
                @Override
                public void perform(HttpHeaders headers) {
                    HttpEntity request = new HttpEntity(headers);
                    delete("/admin/api/processes/{name}", request, process.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientItsnowProcess waitFinished(final ClientItsnowProcess process, final String jobType) throws Exception {
        try {
            return withLoginUser(new Callback<ClientItsnowProcess>() {
                @Override
                public ClientItsnowProcess perform(HttpHeaders headers) {
                    HttpEntity<ClientItsnowProcess> request = new HttpEntity<ClientItsnowProcess>(headers);
                    String job = process.getConfiguration().getProperty(jobType);
                    return getForObject("/admin/api/processes/{name}/wait_finished/{job}", ClientItsnowProcess.class, request, process.getName(), job);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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

}
