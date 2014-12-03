/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import net.happyonroad.platform.repository.DatabaseConfig;
import dnt.itsnow.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class GroupRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/create_roles.sql@up",
                "classpath:META-INF/setup/create_authorities.sql@up",
                "classpath:META-INF/setup/create_accounts.sql",
                "classpath:META-INF/setup/create_users.sql@up",
                "classpath:META-INF/setup/create_groups.sql@up",
                "classpath:META-INF/setup/create_group_authorities.sql@up",
                "classpath:META-INF/setup/create_group_members.sql@up",

                "classpath:META-INF/setup/initialization_roles.sql@up",

                "classpath:META-INF/setup/insert_roles.sql@up",
                "classpath:META-INF/setup/insert_authorities.sql@up",
                "classpath:META-INF/setup/insert_accounts.sql",
                "classpath:META-INF/setup/insert_users.sql@up",
                "classpath:META-INF/setup/insert_groups.sql@up",
                "classpath:META-INF/setup/insert_group_authorities.sql@up",
                "classpath:META-INF/setup/insert_group_members.sql@up"
        };
    }
}
