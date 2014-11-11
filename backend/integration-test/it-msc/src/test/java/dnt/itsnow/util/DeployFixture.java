/**
 * Developer: Kadvin Date: 14-9-17 下午7:20
 */
package dnt.itsnow.util;

import dnt.itsnow.model.*;

import java.util.Properties;

/**
 * 集成测试所使用的测试数据
 */
public class DeployFixture {

    public static ClientAccountRegistration testRegistration() {
        ClientAccountRegistration registration = new ClientAccountRegistration();
        registration.setType("Enterprise");
        registration.setAsUser(true);

        ClientAccount account = new ClientAccount();
        account.setName("it-account");
        account.setDomain("it-domain");
        account.setType("base");
        registration.setAccount(account);

        ClientUser user = new ClientUser();
        user.setName("it-user");
        user.setEmail("test@it.com");
        user.setPhone("12345678901");
        user.setPassword("123456");
        user.setRepeatPassword("123456");
        registration.setUser(user);
        return registration;
    }

    public static ClientItsnowHost testHost() {
        ClientItsnowHost creating = new ClientItsnowHost();
        creating.setName("srv7.itsnow.com");
        creating.setAddress("172.16.3.30");
        creating.setType(ClientHostType.COM);
        creating.setCapacity(5);
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "dev@dnt");
        properties.setProperty("msu.version", "0.2.3-SNAPSHOT");
        properties.setProperty("msp.version", "0.2.3-SNAPSHOT");
        creating.setConfiguration(properties);
        return creating;
    }
}
