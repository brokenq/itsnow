/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.repository.DatabaseConfig;
import dnt.itsnow.platform.util.BeanFilter;
import dnt.itsnow.test.config.RepositoryConfigWithH2;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h1>为Mutable Account 模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class MutableAccountRepositoryConfig extends RepositoryConfigWithH2 implements BeanFilter {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/prepare_schema.sql",
                "classpath:META-INF/migrate/20140722112724_create_accounts.sql@up",
                "classpath:META-INF/migrate/20140728142611_insert_accounts.sql@up"
        };
    }

    public BeanFilter repositoryFilter(){
        return this;
    }

    @Override
    public boolean accept(String beanName, BeanDefinition definition) {
        return !beanName.equals("commonAccountRepository");
    }
}
