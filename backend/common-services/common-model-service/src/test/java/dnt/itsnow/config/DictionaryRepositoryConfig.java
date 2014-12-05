/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import net.happyonroad.platform.repository.DatabaseConfig;
import net.happyonroad.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Process Dictionary模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class DictionaryRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/create_dictionaries.sql@up",
                "classpath:META-INF/setup/initialization.sql",
                "classpath:META-INF/setup/insert_dictionaries.sql"
        };
    }
}
