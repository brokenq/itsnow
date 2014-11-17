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
                "classpath:META-INF/setup/create_dictionaries.sql@up",
                "classpath:META-INF/setup/create_act_re_procdef.sql",
                "classpath:META-INF/setup/create_act_re_deployment.sql",
                "classpath:META-INF/setup/create_private_service_catalogs.sql",
                "classpath:META-INF/setup/create_private_service_items.sql",
                "classpath:META-INF/setup/create_public_service_catalogs.sql",
                "classpath:META-INF/setup/create_public_service_items.sql",
                "classpath:META-INF/setup/create_workflows.sql@up",

                "classpath:META-INF/setup/insert_dictionaries.sql",
                "classpath:META-INF/setup/insert_act_re_procdef.sql",
                "classpath:META-INF/setup/insert_public_service_catalogs.sql",
                "classpath:META-INF/setup/insert_public_service_items.sql",
                "classpath:META-INF/setup/insert_workflows.sql"
        };
    }
}
