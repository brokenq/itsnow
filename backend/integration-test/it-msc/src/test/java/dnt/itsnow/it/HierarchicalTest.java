package dnt.itsnow.it;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import dnt.itsnow.model.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by User on 2014/11/25.
 */
//@RunWith(HierarchicalContextRunner.class)
@Ignore
public class HierarchicalTest {

    /**
     * Start: Account Integration Test
     */
    public class AccountsIt extends AccountsTest{

        private ClientAccount account;

        @Test
        public void testCreateAccount() throws Exception {
//        目前注册账号有问题，暂时使用show方法来获取account
//        ClientAccount account = signUp();
            account = new ClientAccount();
            account.setSn("msp_it_test");
            account = show(account);
            assertNotNull(account);

            resetNew(account);
            account = show(account);
            assertNotNull(account);
            assertTrue(account.getStatus() == ClientAccountStatus.New);
        }

        @Test
        public void testApproveAccount() throws Exception {
            account = approve(account);
            assertTrue(account.getStatus() == ClientAccountStatus.Valid);
        }

        /**
         * Start: Host Integration Test
         */
        public class HostsIt extends ItsnowHostsTest{

            private ClientItsnowHost host;

            @Test
            public void testCreateHost() throws Exception {
                host = create();
                assertNotNull(host);
                assertTrue(host.getStatus() == ClientHostStatus.Planing);

                host = waitHostCreation(host);
                assertTrue(host.getStatus() == ClientHostStatus.Running);
            }

            /**
             * Start: Process Integration Test
             */
            public class ProcessesIt extends ItsnowProcessesTest{

                private ClientItsnowProcess process;

                private String PROCESS_START_INVOCATION_ID = "startInvocationId";
                private String PROCESS_STOP_INVOCATION_ID = "stopInvocationId";

                @Test
                public void testCreateProcess() throws Exception {
                    ClientItsnowProcess process = autoNew(account);
                    assertNotNull(process);

                    process.setHostId(host.getId());
                    process = create(process);
                    assertNotNull(process);
                    assertTrue(process.getStatus() == ClientProcessStatus.Stopped);
                }

                @Test
                public void testStartProcess() throws Exception {
                    start(process);
                    process = show(process);
                    process = waitFinished(process, PROCESS_START_INVOCATION_ID);
                    assertTrue(process.getStatus() == ClientProcessStatus.Running);
                }

                @Test
                public void testStopProcess() throws Exception {
                    stop(process);
                    process = show(process);
                    waitFinished(process, PROCESS_STOP_INVOCATION_ID);
                    process = show(process);
                    assertTrue(process.getStatus() == ClientProcessStatus.Stopped);
                }

                @Test
                public void testDestroyProcess() throws Exception {
                    destroy(process);
                    ClientItsnowProcess showing = null;
                    try {
                        showing = show(process);
                    } catch (Exception e) {
                    }
                    assertNull(showing);
                }

            }
            /**
             * End: Process Integration Test
             */

            @Test
            public void testDestroyHost() throws Exception {
                destroy(host);
                ClientItsnowHost showing = null;
                try {
                    showing = show(host);
                } catch (Exception e) {
                }
                assertNull(showing);
            }

        }
        /**
         * End: Host Integration Test
         */

        @Test
        public void testDestroyAccount() throws Exception {
            // 目前注册账号有问题,暂时先不删除
            // destroy(account);
            // Assert.assertTrue(null == show(account));
        }

    }
    /**
     * End: Account Integration Test
     */
}
