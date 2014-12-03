/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import net.happyonroad.platform.service.AutoNumberService;
import net.happyonroad.platform.support.AutoNumberInMemory;
import dnt.itsnow.service.MutableAccountService;
import dnt.itsnow.service.MutableUserService;
import dnt.itsnow.support.MutableAccountManager;
import dnt.itsnow.support.MutableUserManager;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import org.springframework.context.annotation.ComponentScan;

/**
 * <h1>为Mutable Account Manager的测试提供测试环境</h1>
 *
 * 这种测试有两种方式：
 * <ul>
 * <li>方式1：对底层Repository(MutableAccountRepository)进行mock</li>
 * <li>方式2：基于底层已经ok的测试，也就是基于Repository层的config</li>
 * </ul>
 * 两种方式均可以，一般而言，应该用第1种方式，这里演示了第2种方式
 */
@Configuration
// 在测试环境中不应该采用 ComponentScan，而应该手工构建被测试的对象
//   因为这种模式与实际程序的运行情况不一致，实际程序在组件框架(spring-component-framework)的隔离环境下运行
//   不会出现许多bean的冲突
//@ComponentScan("dnt.itsnow.support") // does not work: can use beans in imported config
// Repository Scan 也存在类似的问题，不过，我采用了 子config提供过滤器的机制

public class MutableAccountManagerConfig2 extends MutableAccountRepositoryConfig {
    @Bean
    public MutableAccountService mutableAccountService(){
        return new MutableAccountManager();
    }

    @Bean
    public AutoNumberService autoNumberService(){
        return new AutoNumberInMemory();
    }


    // 增加了 mutalbe user service


    @Override
    protected String[] sqlScripts() {
        String[] accountScripts = super.sqlScripts();
        String[] allScripts = new String[accountScripts.length + 1];
        System.arraycopy(accountScripts, 0, allScripts, 0, accountScripts.length);
        allScripts[accountScripts.length] = "classpath:META-INF/migrate/20140722125016_create_users.sql@up";
        return allScripts;
    }

    @Bean
    public MutableUserService mutableUserService(){
        return new MutableUserManager();
    }

    @Override
    public boolean accept(String beanName, BeanDefinition definition) {
        return super.accept(beanName, definition) && !beanName.equals("commonUserRepository");
    }
}
