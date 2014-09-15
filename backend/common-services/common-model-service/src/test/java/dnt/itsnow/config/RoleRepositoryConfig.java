/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Process Dictionar模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class RoleRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/migrate/20140726154504_create_roles.sql@up",
                "classpath:META-INF/migrate/20140726154600_insert_roles.sql@up",
                "classpath:META-INF/migrate/20140722135407_create_groups.sql@up",
                "classpath:META-INF/migrate/20140728161920_insert_groups.sql@up",
                "classpath:META-INF/migrate/20140912164525_create_group_roles.sql@up",
                "classpath:META-INF/migrate/20140912164639_insert_group_roles.sql@up"
        };
    }
}
