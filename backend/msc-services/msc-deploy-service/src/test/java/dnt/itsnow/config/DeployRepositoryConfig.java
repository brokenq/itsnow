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
 * <h1>为Deploy Service模块提供的单元测试环境</h1>
 */
@Configuration
@Import(DatabaseConfig.class)
public class DeployRepositoryConfig extends RepositoryConfigWithH2 implements BeanFilter {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/migrate/20140722112724_create_accounts.sql@up",
                "classpath:META-INF/migrate/20140827112025_create_itsnow_hosts.sql@up",
                "classpath:META-INF/migrate/20140827112030_create_itsnow_schemas.sql@up",
                "classpath:META-INF/migrate/20140827112032_create_itsnow_processes.sql@up",
                "classpath:META-INF/migrate/20140728142611_insert_accounts.sql@up",
                "classpath:META-INF/migrate/20140827112047_insert_itsnow_hosts.sql@up",
                "classpath:META-INF/migrate/20140827142537_insert_itsnow_schemas.sql@up",
                "classpath:META-INF/migrate/20140827142851_insert_itsnow_processes.sql@up"
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
