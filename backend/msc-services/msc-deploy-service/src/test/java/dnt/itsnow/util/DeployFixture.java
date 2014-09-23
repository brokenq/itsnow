/**
 * Developer: Kadvin Date: 14-9-17 下午7:20
 */
package dnt.itsnow.util;

import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.model.ItsnowSchema;

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
        return host;
    }

    public static ItsnowProcess testProcess() {
        ItsnowProcess process = new ItsnowProcess();
        process.setName("itsnow-msu_001");
        process.setAccountId(1L);
        process.setHostId(1L);
        process.setSchemaId(1L);
        process.setWd("/opt/releases/msu_001");
        process.setDescription("A test process");
        return process;
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
        return schema;
    }
}
