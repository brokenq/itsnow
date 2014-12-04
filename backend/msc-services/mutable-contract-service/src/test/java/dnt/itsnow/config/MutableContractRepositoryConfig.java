/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Process Dictionary模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class MutableContractRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/create_accounts.sql",
                "classpath:META-INF/setup/create_users.sql",
                "classpath:META-INF/setup/create_contracts.sql",
                "classpath:META-INF/setup/create_contract_details.sql",
                "classpath:META-INF/setup/create_contract_users.sql",
                "classpath:META-INF/setup/create_contract_records.sql",

                "classpath:META-INF/setup/init.sql",

                "classpath:META-INF/setup/insert_accounts.sql",
                "classpath:META-INF/setup/insert_users.sql",
                "classpath:META-INF/setup/insert_contracts.sql",
                "classpath:META-INF/setup/insert_contract_users.sql",
                "classpath:META-INF/setup/insert_contract_records.sql"
        };
    }
}
