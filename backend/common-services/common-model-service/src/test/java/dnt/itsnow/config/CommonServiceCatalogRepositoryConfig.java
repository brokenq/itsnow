package dnt.itsnow.config;

import net.happyonroad.platform.repository.DatabaseConfig;
import net.happyonroad.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Common ServiceCatalog 模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class CommonServiceCatalogRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        // 由于 common 模块并不依赖于mutable模块，而且也无法依赖
        // 所以，为了做这些测试，需要copy一份mutable schema过来
        return new String[]{
                "classpath:META-INF/setup/prepare_schema.sql",
                "classpath:META-INF/setup/create_public_service_catalogs.sql",
                "classpath:META-INF/setup/create_public_service_items.sql",
                "classpath:META-INF/setup/insert_public_service_catalogs.sql",
                "classpath:META-INF/setup/insert_public_service_items.sql"
        };
    }
}
