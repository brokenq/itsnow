/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.test.config;

import dnt.itsnow.platform.repository.RepositoryScanner;
import dnt.itsnow.platform.repository.support.MybatisRepositoryScanner;
import dnt.itsnow.test.support.H2AsMySqlEmbeddedDsConfigurer;
import dnt.itsnow.test.support.MigrateResourcePopulator;
import dnt.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.init.DatabasePopulator;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * <h1>共享的 Test Config</h1>
 */
@Profile("test")
public abstract class TestConfigWithH2 implements InitializingBean {
    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public DataSource dataSource(DatabasePopulator populator) throws SQLException {
        EmbeddedDatabaseFactory dbFactory = new EmbeddedDatabaseFactory();
        dbFactory.setDatabaseConfigurer(new H2AsMySqlEmbeddedDsConfigurer());
        dbFactory.setDatabasePopulator(populator);
        return new SingleConnectionDataSource(dbFactory.getDatabase().getConnection(), true);
    }

    @Bean
    DatabasePopulator databasePopulator() {
        MigrateResourcePopulator populator = new MigrateResourcePopulator();
        populator.setIgnoreFailedDrops(true);
        for (String script : sqlScripts()) {
            Resource resource;
            String direction = null;
            if( script.contains("@") ){
                direction = StringUtils.substringAfter(script, "@").trim();
                script = StringUtils.substringBefore(script, "@").trim();
            }
            resource = applicationContext.getResource(script);
            populator.addScript(resource, direction);
        }
        return populator;
    }

    protected abstract String[] sqlScripts();

    protected  String dbRepository() {
        return "dnt.itsnow.repository";
    }

    @Bean
    public RepositoryScanner repositoryScanner(){
        return new MybatisRepositoryScanner(applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RepositoryScanner scanner = repositoryScanner();
        // TODO 现在的 configuration 没有排序
        scanner.configure("classpath*:META-INF/mybatis.xml");
        int count = scanner.scan(dbRepository());
        System.out.println("Found " + count + " db repositories");
    }


}