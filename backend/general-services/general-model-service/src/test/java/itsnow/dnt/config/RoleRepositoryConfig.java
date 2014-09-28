/**
 * xiongjie on 14-8-6.
 */
package itsnow.dnt.config;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为角色管理模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class RoleRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/create_process_dictionaries.sql@up",
                "classpath:META-INF/setup/create_work_times.sql@up",
                "classpath:META-INF/setup/create_sites.sql@up",
                "classpath:META-INF/setup/create_departments.sql@up",
                "classpath:META-INF/setup/create_site_depts.sql@up",
                "classpath:META-INF/setup/create_roles.sql@up",
                "classpath:META-INF/setup/create_authorities.sql@up",
                "classpath:META-INF/setup/create_accounts.sql",
                "classpath:META-INF/setup/create_users.sql@up",
                "classpath:META-INF/setup/create_staffs.sql@up",

                "classpath:META-INF/setup/initialization_roles.sql@up",

                "classpath:META-INF/setup/insert_process_dictionaries.sql@up",
                "classpath:META-INF/setup/insert_work_times.sql@up",
                "classpath:META-INF/setup/insert_sites.sql@up",
                "classpath:META-INF/setup/insert_departments.sql@up",
                "classpath:META-INF/setup/insert_site_depts.sql@up",
                "classpath:META-INF/setup/insert_roles.sql@up",
                "classpath:META-INF/setup/insert_authorities.sql@up",
                "classpath:META-INF/setup/insert_accounts.sql",
                "classpath:META-INF/setup/insert_users.sql@up",
                "classpath:META-INF/setup/insert_staffs.sql@up"
        };
    }
}
