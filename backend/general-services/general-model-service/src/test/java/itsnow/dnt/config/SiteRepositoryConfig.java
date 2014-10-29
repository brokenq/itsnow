/**
 * xiongjie on 14-8-6.
 */
package itsnow.dnt.config;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为WorkTime模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class SiteRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/create_accounts.sql",
                "classpath:META-INF/setup/create_users.sql",
                "classpath:META-INF/setup/create_staffs.sql",
                "classpath:META-INF/migrate/20140720144647_create_dictionaries.sql@up",
                "classpath:META-INF/migrate/20140721113250_create_work_times.sql@up",
                "classpath:META-INF/migrate/20140722130944_create_sites.sql@up",
                "classpath:META-INF/migrate/20140722130950_create_departments.sql@up",
                "classpath:META-INF/migrate/20140902160038_create_site_depts.sql@up",

                "classpath:META-INF/setup/initialization_site.sql",

                "classpath:META-INF/setup/insert_accounts.sql",
                "classpath:META-INF/setup/insert_users.sql",
                "classpath:META-INF/setup/insert_staffs.sql",
                "classpath:META-INF/setup/insert_dictionaries.sql",
                "classpath:META-INF/setup/insert_work_times.sql",
                "classpath:META-INF/setup/insert_sites.sql",
                "classpath:META-INF/setup/insert_departments.sql",
                "classpath:META-INF/setup/insert_site_depts.sql"
        };
    }
}
