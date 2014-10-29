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
public class WorkTimeRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/migrate/20140721113250_create_work_times.sql@up",
                "classpath:META-INF/setup/insert_work_times.sql@down",
                "classpath:META-INF/setup/insert_work_times.sql@up"
        };
    }
}
