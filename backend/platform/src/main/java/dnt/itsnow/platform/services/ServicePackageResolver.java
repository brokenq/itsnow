/**
 * Developer: Kadvin Date: 14-7-14 下午5:01
 */
package dnt.itsnow.platform.services;

import dnt.util.StringUtils;
import net.happyonroad.component.container.feature.AbstractFeatureResolver;
import net.happyonroad.component.core.Component;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Itsnow平台上的服务模块扩展包
 * 主要扩展内容有：
 *
 * <ol>
 * <li>DB/Migrate脚本扩展</li>
 * <li>DB/Repository,Mapper类扩展</li>
 * <li>Spring Controller/Views等扩展</li>
 * </ol>
 */
public class ServicePackageResolver extends AbstractFeatureResolver {
    public static final String FEATURE           = "service";
    public static final String REPOSITORY        = "App-Repository";

    public ServicePackageResolver() {
        super(50, 50);
    }

    @Override
    public String getName() {
        return FEATURE;
    }

    @Override
    public boolean hasFeature(Component component) {
        //暂时仅根据组件的artifact id判断，也不根据内容判断
        return component.getArtifactId().endsWith("_app");
    }

    @Override
    public void resolve(Component component) throws Exception {
        logger.info("The {} is resolved for service feature", component);
        String repository = component.getManifestAttribute(REPOSITORY);
        if(StringUtils.isNotBlank(repository)){
            scanMyBatisRepository(component.getApplication(), repository);
        }
    }

    void scanMyBatisRepository(ApplicationContext application, String repository) {
        ClassPathMapperScanner scanner = new ClassPathMapperScanner((BeanDefinitionRegistry) application);

        SqlSessionFactory sqlSessionFactory = application.getBean(SqlSessionFactory.class);
        SqlSessionTemplate sqlSessionTemplate = application.getBean(SqlSessionTemplate.class);
        String sqlSessionFactoryBeanName = "sqlSessionFactory";
        String sqlSessionTemplateBeanName = "sqlSessionTemplate";

        scanner.setSqlSessionFactory(sqlSessionFactory);
        scanner.setSqlSessionTemplate(sqlSessionTemplate);
        scanner.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
        scanner.setSqlSessionTemplateBeanName(sqlSessionTemplateBeanName);
        scanner.setResourceLoader(application);
        scanner.registerFilters();
        scanner.scan(StringUtils.split(repository, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }
}
