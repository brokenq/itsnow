/**
 * Developer: Kadvin Date: 14-7-14 下午5:15
 */
package dnt.itsnow.platform;

import dnt.itsnow.platform.web.support.ExtendedRequestMappingHandlerMapping;
import net.happyonroad.spring.service.AbstractServiceConfig;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.sql.DataSource;

/**
 * 平台对各个扩展模块的服务配置（主要是暴露哪些服务出去）
 */
public class ServiceConfiguration extends AbstractServiceConfig {
    @Override
    public void defineServices() {
        //数据库相关服务
        exportService(DataSource.class);
        exportService(SqlSessionFactory.class);
        //Spring MVC相关服务
        exportService(ExtendedRequestMappingHandlerMapping.class);
        //工作流相关服务
    }
}
