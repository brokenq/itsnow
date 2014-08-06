/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.service.AutoNumberService;
import dnt.itsnow.platform.support.AutoNumberInMemory;
import dnt.itsnow.service.MutableAccountService;
import dnt.itsnow.support.MutableAccountManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import org.springframework.context.annotation.ComponentScan;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
// 在测试环境中不应该采用 ComponentScan，而应该手工构建被测试的对象
//   因为这种模式与实际程序的运行情况不一致，实际程序在组件框架(spring-component-framework)的隔离环境下运行
//   不会出现许多bean的冲突
//@ComponentScan("dnt.itsnow.support") // does not work: can use beans in imported config
public class MutableAccountManagerConfig extends MutableAccountRepositoryConfig {
    @Bean
    public MutableAccountService commonAccountService(){
        return new MutableAccountManager();
    }

    @Bean
    public AutoNumberService autoNumberService(){
        return new AutoNumberInMemory();
    }
}
