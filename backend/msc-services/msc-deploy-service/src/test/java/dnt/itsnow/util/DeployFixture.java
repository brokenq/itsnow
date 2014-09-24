/**
 * Developer: Kadvin Date: 14-9-17 下午7:20
 */
package dnt.itsnow.util;

import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.model.MsuAccount;

import java.util.Properties;

/**
 * 发布相关模型所使用的测试数据
 */
public class DeployFixture {
    public static ItsnowHost testHost(){
        ItsnowHost host = new ItsnowHost();
        host.setAddress("192.168.0.100");
        host.setName("srv1.itsnow.com");
        host.setCapacity(10);
        host.setDescription("A test itsnow host");
        Properties configuration = new Properties();
        configuration.setProperty("user", "root");
        configuration.setProperty("password", "root1234");
        host.setConfiguration(configuration);
        host.creating();
        return host;
    }

    public static ItsnowHost deployHost(){
        ItsnowHost host = DeployFixture.testHost();
        host.setId(1001L);
        host.setAddress("srv2.itsnow.com");
        host.setName("srv2.itsnow.com");
        host.setProperty("password", "kadvin1234");
        host.setProperty("msu.version", "0.1.8");
        host.setProperty("msp.version", "0.1.8");
        host.setProperty("msc.version", "0.1.8");
        return host;
    }

    public static ItsnowSchema testSchema() {
        ItsnowSchema schema = new ItsnowSchema();
        schema.setName("itsnow_test");
        schema.setHostId(1L);
        schema.setDescription("The test schema");
        Properties configuration = new Properties();
        configuration.setProperty("user", "itsnow");
        configuration.setProperty("password", "secret");
        schema.setConfiguration(configuration);
        schema.creating();
        return schema;
    }

    public static ItsnowSchema deploySchema() {
        ItsnowSchema schema = testSchema();
        schema.setId(99L);
        ItsnowHost host = deployHost();
        schema.setHost(host);
        schema.setHostId(host.getId());
        schema.setProperty("user", "itsnow_test");
        schema.setProperty("password", "1234abcd");
        return schema;
    }

    public static ItsnowProcess testProcess() {
        ItsnowProcess process = new ItsnowProcess();
        process.setName("itsnow_msu_001");
        process.setAccountId(1L);
        process.setHostId(1L);
        process.setSchemaId(1L);
        process.setWd("/opt/releases/msu_001");
        process.setDescription("A test process");
        process.creating();
        return process;
    }

    public static ItsnowProcess deployProcess() {
        ItsnowProcess process = testProcess();
        process.setName("itsnow_msu_100");
        process.setAccountId(1L);
        process.setProperty("rmi.port", "1234");
        process.setProperty("jmx.port", "4321");
        process.setProperty("debug.port", "5322");
        process.setProperty("http.port", "8012");

        MsuAccount account = new MsuAccount();
        account.setDomain("msu_100");
        process.setAccount(account);


        ItsnowSchema schema = deploySchema();
        process.setSchemaId(schema.getId());
        process.setSchema(schema);

        ItsnowHost host = schema.getHost();
        process.setHost(host);
        process.setHostId(host.getId());

        process.setWd("/opt/releases/msu_100");
        process.setDescription("A test process");
        return process;
    }
}
