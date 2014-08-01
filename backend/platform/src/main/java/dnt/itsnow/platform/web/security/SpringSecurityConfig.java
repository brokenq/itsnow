/**
 * Developer: Kadvin Date: 14-7-16 下午9:53
 */
package dnt.itsnow.platform.web.security;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/** Work as parent of SpringMvcConfig */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter implements DelegateSecurityConfigurer {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(delegateAuthenticationProvider())
                //.userDetailsService(delegateUserDetailsService())
                /* 配置 remember me*/
                .rememberMe()
                .authenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .tokenRepository(delegateTokenRepository())
                .tokenValiditySeconds(60 * 60 * 24 * 30)//一个月
                .useSecureCookie(true)
                /* 配置匿名服务 */
                .and().anonymous().authorities("ROLE_ANONYMOUS").principal("ANONYMOUS")
                /* 配置登出服务 */
                .and().logout().invalidateHttpSession(true).logoutSuccessUrl("/login.html").logoutUrl("/logout")
                /* 配置会话服务 */
                .and().sessionManagement().enableSessionUrlRewriting(true).sessionFixation().none();
        // 若以后支持手机客户端访问，那个时候可能就需要基于Digest-Authentication
        //noinspection unchecked
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authenticated =
                (ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)
                        //谁在前，谁优先级高
                        http.authorizeRequests()
                            .antMatchers("/api/**").hasAnyRole("ADMIN","MONITOR","REPORTER","USER")
                            .antMatchers("/admin/api/**").hasAnyRole("ADMIN")
                            .antMatchers("/monitor/api/**").hasAnyRole("MONITOR")
                            .antMatchers("/reporter/api/**").hasAnyRole("REPORTER")
                            .antMatchers("/**").permitAll();
        authenticated.and().formLogin().loginPage("/login.html")
                     .and().httpBasic().realmName("ItsNow Platform");
    }

    @Bean
    DelegateAuthenticationProvider delegateAuthenticationProvider() {
        return new DelegateAuthenticationProvider();
    }

//    @Bean
//    DelegateUserDetailsService delegateUserDetailsService() {
//        return new DelegateUserDetailsService();
//    }

    @Bean
    DelegatePersistentTokenRepository delegateTokenRepository() {
        return new DelegatePersistentTokenRepository();
    }

    // Delegate Security Configurer

    @Override
    public DelegateSecurityConfigurer delegate(AuthenticationProvider delegate) {
        delegateAuthenticationProvider().setDelegate(delegate);
        return this;
    }

    @Override
    public DelegateSecurityConfigurer delegate(PersistentTokenRepository delegate) {
        delegateTokenRepository().setDelegate(delegate);
        return this;
    }

//    @Override
//    public DelegateSecurityConfigurer delegate(UserDetailsService delegate) {
//        delegateUserDetailsService().setDelegate(delegate);
//        return this;
//    }
}
