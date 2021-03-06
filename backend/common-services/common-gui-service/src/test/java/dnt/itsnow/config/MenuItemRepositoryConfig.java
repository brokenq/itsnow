/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Common MenuItem 模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class MenuItemRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        // 由于 common 模块并不依赖于mutable模块，而且也无法依赖
        // 所以，为了做这些测试，需要copy一份mutable schema过来
        return new String[]{
                "classpath:META-INF/migrate/20140819094130_create_menu_items.sql@up",
                "classpath:META-INF/setup/20140825140057_insert_msc_menu_items.sql"
        };
    }
}
