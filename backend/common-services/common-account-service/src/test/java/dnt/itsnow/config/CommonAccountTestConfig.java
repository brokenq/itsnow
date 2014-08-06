/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.test.config.TestConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Common Account 模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class CommonAccountTestConfig extends TestConfigWithH2  {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/prepare_schema.sql",
                "classpath:META-INF/setup/create_accounts.sql",
                "classpath:META-INF/setup/insert_accounts.sql",
                "classpath:META-INF/setup/activate_schema.sql"
        };
    }
}
