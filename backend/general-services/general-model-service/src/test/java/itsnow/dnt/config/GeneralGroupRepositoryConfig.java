/**
 * xiongjie on 14-8-6.
 */
package itsnow.dnt.config;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Process Dictionary模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class GeneralGroupRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/create_roles.sql",
                "classpath:META-INF/setup/create_groups.sql",
                "classpath:META-INF/setup/create_authorities.sql",
                "classpath:META-INF/setup/create_group_authorities.sql",
                "classpath:META-INF/setup/create_accounts.sql",
                "classpath:META-INF/setup/create_users.sql",
                "classpath:META-INF/setup/create_group_members.sql",

                "classpath:META-INF/setup/initialization_groups.sql",

                "classpath:META-INF/setup/insert_roles.sql",
                "classpath:META-INF/setup/insert_groups.sql",
                "classpath:META-INF/setup/insert_authorities.sql",
                "classpath:META-INF/setup/insert_group_authorities.sql",
                "classpath:META-INF/setup/insert_accounts.sql",
                "classpath:META-INF/setup/insert_users.sql",
                "classpath:META-INF/setup/insert_group_members.sql"
        };
    }
}
