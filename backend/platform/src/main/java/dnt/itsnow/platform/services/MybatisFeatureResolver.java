/**
 * Developer: Kadvin Date: 14-7-14 下午5:01
 */
package dnt.itsnow.platform.services;

import dnt.util.StringUtils;
import net.happyonroad.component.container.feature.AbstractFeatureResolver;
import net.happyonroad.component.core.Component;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.springframework.context.ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS;

/**
 * <h2>Itsnow平台上的服务模块扩展包</h2>
 * <p/>
 * 主要扩展内容有：
 * <p/>
 * <ol>
 * <li>DB {Repository | Mapper}类扩展</li>
 * </ol>
 * <p/>
 * 主要扩展原理是：
 * <pre>
 * 在Spring Service Context加载之后，Spring Application Context加载之前
 * 将需要扩展的对象，调用平台对应的服务构建出来，并注入到对应的Service Context里面去
 * 这样，他们就可以被业务包中的beans访问到
 * </pre>
 */
public class MybatisFeatureResolver extends AbstractFeatureResolver {
    public static final String FEATURE       = "mybatis";
    public static final String DB_CONFIG     = "DB-Config";
    public static final String DB_REPOSITORY = "DB-Repository";

    public MybatisFeatureResolver() {
        //28： 在Spring Service Context之后(25)， Spring Application Context之前(30)，加载
        // 这样可以为相应的context准备好
        //  repository(mybatis mapper)
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
        return StringUtils.isNotBlank(component.getManifestAttribute(DB_REPOSITORY));
    }

    @Override
    public void resolve(Component component) throws Exception {
        String dbRepository = component.getManifestAttribute(DB_REPOSITORY);
        String dbConfig = component.getManifestAttribute(DB_CONFIG);
        scanMybatisConfig(component.getServiceApplication(), dbConfig);
        scanMybatisRepository(component.getServiceApplication(), dbRepository);
        logger.info("The {} is resolved for mybatis feature", component);
    }

    private void scanMybatisConfig(ApplicationContext application, String config) throws IOException {
        if( StringUtils.isBlank(config)) config = "classpath?:META-INF/mybatis.xml";
        Resource resource = application.getResource(config);
        if( resource == null || !resource.exists()) return;

        logger.info("Found extra mybatis config in {}", config);

        InputStream stream = resource.getInputStream();
        ClassLoader legacyClassLoader = Resources.getDefaultClassLoader();
        try {
            Resources.setDefaultClassLoader(application.getClassLoader());
            Configuration configuration = application.getBean(Configuration.class);
            XMLConfigBuilder builder = new XMLConfigBuilder(stream,
                                                            configuration.getEnvironment().toString(),
                                                            configuration.getVariables());
            Configuration extraConfiguration = builder.getConfiguration();
            builder.parse();
            //将新定义的变量设置到原configuration上
            configuration.setVariables(extraConfiguration.getVariables());
            //将新定义的类型别名注册上去
            Map<String,Class<?>> typeAliases = extraConfiguration.getTypeAliasRegistry().getTypeAliases();
            Map<String, Class<?>> existAliases = configuration.getTypeAliasRegistry().getTypeAliases();
            for (Map.Entry<String, Class<?>> entry : typeAliases.entrySet()) {
                if( existAliases.containsKey(entry.getKey()) ) continue;
                configuration.getTypeAliasRegistry().registerAlias(entry.getKey(), entry.getValue());
                logger.debug("Registered type alias: '{}' -> '{}'", entry.getValue(), entry.getKey());
            }
            //将新定义的 plugin 注册上去
            List<Interceptor> existInterceptors = configuration.getInterceptors();
            for(Interceptor plugin : extraConfiguration.getInterceptors()){
                if ( existInterceptors.contains(plugin) ) continue;
                configuration.addInterceptor(plugin);
                logger.debug("Registered plugin: '{}'", plugin);
            }
            // 将新定义的type handler注册上去
            Collection<String> existTypeHandlerNames = new HashSet<String>();
            for (TypeHandler<?> typeHandler : configuration.getTypeHandlerRegistry().getTypeHandlers()) {
                existTypeHandlerNames.add(typeHandler.toString());
            }
            for (TypeHandler<?> typeHandler : extraConfiguration.getTypeHandlerRegistry().getTypeHandlers()) {
                if( existTypeHandlerNames.contains(typeHandler.toString())) continue;
                configuration.getTypeHandlerRegistry().register(typeHandler);
                logger.debug("Registered type handler: '{}'", typeHandler);
            }
            // 暂时限制： ，
            //   Object Factory,
            //   Object Wrapper Factory,
            //   Settings
            //   Environments
            //   Mappers, 额外的config里面不能配置mapper规则 mapper规则必须通过DB-Repository属性配置
            //   databaseIdProvider
            // 等暂时均不可以额外配置
        } finally {
            Resources.setDefaultClassLoader(legacyClassLoader);
            stream.close();
        }

    }

    void scanMybatisRepository(ApplicationContext application, String appRepository) {
        ClassPathMapperScanner scanner = new ClassPathMapperScanner((BeanDefinitionRegistry) application);

        SqlSessionFactory sqlSessionFactory = application.getBean(SqlSessionFactory.class);
        String sqlSessionFactoryBeanName = "sqlSessionFactory";

        scanner.setSqlSessionFactory(sqlSessionFactory);
        scanner.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
        scanner.setResourceLoader(application);
        scanner.setIncludeAnnotationConfig(false);
        scanner.registerFilters();
        int count = scanner.scan(StringUtils.split(appRepository, CONFIG_LOCATION_DELIMITERS));
        logger.debug("Scanned {} mybatis repositories", count);
    }

}
