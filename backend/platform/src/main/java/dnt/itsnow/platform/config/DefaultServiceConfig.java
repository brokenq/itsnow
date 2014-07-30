/**
 * Developer: Kadvin Date: 14-7-11 下午3:49
 */
package dnt.itsnow.platform.config;

import net.happyonroad.spring.service.AbstractServiceConfig;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 缺省的平台扩展应用服务配置（定义默认导入哪些服务）
 *
 * @see  dnt.itsnow.platform.config.DefaultAppConfig
 */
public class DefaultServiceConfig extends AbstractServiceConfig {

    @Override
    public void defineServices() {
        //数据库相关服务
        importService(DataSource.class);
        importService(PlatformTransactionManager.class, "*", "transactionManager");
        importService(SqlSessionFactory.class);
        importService(Configuration.class);
        //Spring MVC相关服务
    }
}
