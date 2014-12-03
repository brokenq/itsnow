/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import net.happyonroad.platform.repository.DatabaseConfig;
import net.happyonroad.platform.util.BeanFilter;
import net.happyonroad.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Deploy Service模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class DeployRepositoryConfig extends RepositoryConfigWithH2  {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/migrate/20140720112724_create_accounts.sql@up",
                "classpath:META-INF/migrate/20140720125016_create_users.sql@up",
                "classpath:META-INF/migrate/20140827112025_create_itsnow_hosts.sql@up",
                "classpath:META-INF/migrate/20140827112030_create_itsnow_schemas.sql@up",
                "classpath:META-INF/migrate/20140827112032_create_itsnow_processes.sql@up",

                "classpath:META-INF/setup/insert_accounts.sql",
                "classpath:META-INF/setup/insert_users.sql",
                "classpath:META-INF/setup/insert_itsnow_hosts.sql",
                "classpath:META-INF/setup/insert_itsnow_schemas.sql",
                "classpath:META-INF/setup/insert_itsnow_processes.sql"
        };
    }
}
