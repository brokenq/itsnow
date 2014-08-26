/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

//import org.springframework.context.annotation.ComponentScan;
import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.support.CommonAccountManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
// 当采用 import resource 方式时，会抛出
//   BeanCurrentlyInCreationException: Error creating bean with name 'sqlSessionFactory'
//@Import(CommonAccountRepositoryTestConfig.class)
// 在测试环境中不应该采用 ComponentScan，而应该手工构建被测试的对象
//   因为这种模式与实际程序的运行情况不一致，实际程序在组件框架(spring-component-framework)的隔离环境下运行
//   不会出现许多bean的冲突
//@ComponentScan("dnt.itsnow.support") // does not work: can use beans in imported config
public class CommonAccountManagerConfig extends CommonAccountRepositoryConfig {

    @Bean
    public CommonAccountService commonAccountService(){
        return new CommonAccountManager();
    }
}
