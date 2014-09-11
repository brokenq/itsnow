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
public class WorkflowRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/migrate/20140901144647_create_process_dictionaries.sql@up",
                "classpath:META-INF/migrate/20140904124504_insert_process_dictionaries.sql@up",
                "classpath:META-INF/setup/create_act_re_procdef.sql@up",
                "classpath:META-INF/setup/create_private_service_catalogs.sql@up",
                "classpath:META-INF/setup/create_private_service_items.sql@up",
                "classpath:META-INF/setup/create_public_service_catalogs.sql@up",
                "classpath:META-INF/setup/create_public_service_items.sql@up",
                "classpath:META-INF/setup/insert_act_re_procdef.sql@up",
                "classpath:META-INF/setup/insert_public_service_catalogs.sql@up",
                "classpath:META-INF/setup/insert_public_service_items.sql@up",
                "classpath:META-INF/migrate/20140902170712_create_workflows.sql@up",
                "classpath:META-INF/migrate/20140910161523_insert_workflows.sql@up"
        };
    }
}
