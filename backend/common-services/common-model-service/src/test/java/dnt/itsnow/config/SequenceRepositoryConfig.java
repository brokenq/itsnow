/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import net.happyonroad.platform.repository.DatabaseConfig;
import dnt.itsnow.test.config.RepositoryConfigWithH2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Sequence 模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class SequenceRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        // 由于 common 模块并不依赖于mutable模块，而且也无法依赖
        // 所以，为了做这些测试，需要copy一份mutable schema过来
        return new String[]{
                "classpath:META-INF/setup/create_sequences.sql",
                "classpath:META-INF/setup/insert_sequences.sql"
        };
    }
}
