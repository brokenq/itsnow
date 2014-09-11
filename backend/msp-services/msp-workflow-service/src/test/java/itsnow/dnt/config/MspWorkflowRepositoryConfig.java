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
public class MspWorkflowRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/create_process_dictionaries.sql@up",
                "classpath:META-INF/setup/insert_process_dictionaries.sql@up",
                "classpath:META-INF/setup/create_act_re_procdef.sql@up",
                "classpath:META-INF/setup/create_public_service_catalogs.sql@up",
                "classpath:META-INF/setup/create_public_service_items.sql@up",
                "classpath:META-INF/setup/insert_act_re_procdef.sql@up",
                "classpath:META-INF/setup/insert_public_service_catalogs.sql@up",
                "classpath:META-INF/setup/insert_public_service_items.sql@up",
                "classpath:META-INF/setup/create_workflows.sql@up",
                "classpath:META-INF/setup/insert_workflows.sql@up"
        };
    }
}
