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
                "classpath:META-INF/migrate/20140720144647_create_dictionaries.sql@up",
                "classpath:META-INF/migrate/20140721113250_create_work_times.sql@up",
                "classpath:META-INF/migrate/20140722130944_create_sites.sql@up",
                "classpath:META-INF/migrate/20140722130950_create_departments.sql@up",
                "classpath:META-INF/migrate/20140902160038_create_site_depts.sql@up",

                "classpath:META-INF/setup/initialization_site.sql",

                "classpath:META-INF/migrate/20140904124504_insert_dictionaries.sql@up",
                "classpath:META-INF/setup/insert_work_times.sql@up",
                "classpath:META-INF/migrate/20140909150904_insert_sites.sql@up",
                "classpath:META-INF/migrate/20140909152504_insert_departments.sql@up",
                "classpath:META-INF/migrate/20140909161704_insert_site_depts.sql@up"
        };
    }
}
