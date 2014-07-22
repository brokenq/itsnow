/**
 * Developer: Kadvin Date: 14-7-11 下午3:49
 */
package dnt.itsnow.platform.services;

import dnt.itsnow.platform.web.support.ExtendedRequestMappingHandlerMapping;
import net.happyonroad.spring.service.AbstractServiceConfig;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.sql.DataSource;

/**
 * 缺省的平台扩展应用服务配置（定义默认导入哪些服务）
 *
 * @see  dnt.itsnow.platform.services.DefaultServiceAppConfig
 */
public class DefaultServiceConfig extends AbstractServiceConfig {

    @Override
    public void defineServices() {
        //数据库相关服务
        importService(DataSource.class);
        importService(SqlSessionFactory.class);
        //Spring MVC相关服务
        importService(ExtendedRequestMappingHandlerMapping.class);
        //工作流相关服务
        importService(ProcessEngineFactoryBean.class);
    }
}
