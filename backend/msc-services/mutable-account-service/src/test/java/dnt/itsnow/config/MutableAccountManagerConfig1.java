/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import net.happyonroad.platform.service.AutoNumberService;
import net.happyonroad.platform.support.AutoNumberInMemory;
import dnt.itsnow.repository.MutableAccountRepository;
import dnt.itsnow.service.MutableAccountService;
import dnt.itsnow.service.MutableUserService;
import dnt.itsnow.support.MutableAccountManager;
import org.jmock.Mockery;
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
 * 两种方式均可以，一般而言，应该用第1种方式，这里演示了第1种方式
 */
@Configuration
public class MutableAccountManagerConfig1 {

    @Bean
    public Mockery mockery(){
        return  new Mockery();
    }

    // 被测试对象
    @Bean
    public MutableAccountService mutableAccountService(){
        return new MutableAccountManager();
    }

    @Bean
    public MutableUserService mutableUserService(){
        return mockery().mock(MutableUserService.class);
    }

    @Bean
    public AutoNumberService autoNumberService(){
        return new AutoNumberInMemory();
    }

    @Bean
    public MutableAccountRepository mutableAccountRepository(){
        return mockery().mock(MutableAccountRepository.class);
    }
}
