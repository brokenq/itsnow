package dnt.itsnow.it;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suites 测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PrepareAccountsTest.class,
        PrepareHostsTest.class,
        PrepareProcessesTest.class,

        AccountsTest.class,
        ItsnowHostsTest.class,
        ItsnowProcessesTest.class,

        RemoveProcessesTest.class,
        RemoveHostsTest.class,
        RemoveAccountsTest.class
})
public class SuitesTest extends AbstractTest {

}
