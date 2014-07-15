/**
 * Developer: Kadvin Date: 14-7-14 下午5:01
 */
package dnt.itsnow.platform.services;

import dnt.util.StringUtils;
import net.happyonroad.component.container.feature.AbstractFeatureResolver;
import net.happyonroad.component.core.Component;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

import static org.springframework.context.ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS;

/**
 * <h2>Itsnow平台上的服务模块扩展包</h2>
 *
 * 主要扩展内容有：
 *
 * <ol>
 * <li>DB {Repository | Mapper}类扩展</li>
 * <li>Spring Controller/Views等扩展</li>
 * </ol>
 *
 * 主要扩展原理是：
 * <pre>
 * 在Spring Service Context加载之后，Spring Application Context加载之前
 * 将需要扩展的对象，调用平台对应的服务构建出来，并注入到对应的Service Context里面去
 * 这样，他们就可以被业务包中的beans访问到
 * </pre>
 */
public class ServicePackageResolver extends AbstractFeatureResolver {
    public static final String FEATURE        = "service-package";
    public static final String APP_REPOSITORY = "App-Repository";
    public static final String WEB_REPOSITORY = "Web-Repository";

    public ServicePackageResolver() {
        //28： 在Spring Service Context之后(25)， Spring Application Context之前(30)，加载
        // 这样可以为相应的context准备好
        //  1. repository(mybatis mapper)
        //  2. controller(spring controller)
        //68:  在Spring Service Context(65) 之后，Spring Application Context(70) 之前卸载
        // 其实没有做任何卸载动作，卸载顺序无所谓
        super(28, 68);
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
        String appRepository = component.getManifestAttribute(APP_REPOSITORY);
        if (StringUtils.isNotBlank(appRepository)) {
            scanMyBatisRepository(component.getServiceApplication(), appRepository);
        }
        String webRepository = component.getManifestAttribute(WEB_REPOSITORY);
        if (StringUtils.isNotBlank(webRepository)) {
            scanSpringControllers(component.getServiceApplication(), webRepository);
        }
        logger.info("The {} is resolved for service feature", component);
    }

    void scanMyBatisRepository(ApplicationContext application, String repository) {
        ClassPathMapperScanner scanner = new ClassPathMapperScanner((BeanDefinitionRegistry) application);

        SqlSessionFactory sqlSessionFactory = application.getBean(SqlSessionFactory.class);
        String sqlSessionFactoryBeanName = "sqlSessionFactory";

        scanner.setSqlSessionFactory(sqlSessionFactory);
        scanner.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
        scanner.setResourceLoader(application);
        scanner.setIncludeAnnotationConfig(false);
        scanner.registerFilters();
        int count = scanner.scan(StringUtils.split(repository, CONFIG_LOCATION_DELIMITERS));
        logger.debug("Scanned {} mybatis repositories", count);
    }

    private void scanSpringControllers(ApplicationContext application, String webRepository) {


    }
}
