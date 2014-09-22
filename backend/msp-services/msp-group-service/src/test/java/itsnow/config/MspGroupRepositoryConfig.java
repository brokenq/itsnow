/**
 * xiongjie on 14-8-6.
 */
package itsnow.config;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Process Dictionar模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class MspGroupRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/create_roles.sql@up",
                "classpath:META-INF/setup/insert_roles.sql@up",
                "classpath:META-INF/setup/create_groups.sql@up",
                "classpath:META-INF/setup/insert_groups.sql@up",
                "classpath:META-INF/setup/create_group_roles.sql@up",
                "classpath:META-INF/setup/insert_group_roles.sql@up"
        };
    }
}