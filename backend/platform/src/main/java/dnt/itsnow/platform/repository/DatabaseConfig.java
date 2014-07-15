/**
 * Developer: Kadvin Date: 14-7-4 上午9:14
 */
package dnt.itsnow.platform.repository;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 关于数据库的配置
 */
@Configuration
public class DatabaseConfig {
    @Bean
    @Profile("production")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        String dbHost = System.getProperty("db.host", "localhost");
        String dbPort = System.getProperty("db.port", "3306");
        String dbName = System.getProperty("db.name", "itsnow_dev");
        String dbUser = System.getProperty("db.user", "itsnow");
        String dbPass = System.getProperty("db.password", "secret");
        String dbUrl = String.format("jdbc:mysql://%s:%s/%s", dbHost, dbPort, dbName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPass);
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource){
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
