/**
 * Developer: Kadvin Date: 14-7-16 下午9:53
 */
package dnt.itsnow.platform.web.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * Work as parent of SpringMvcConfig
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("secret").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //noinspection unchecked
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authenticated =
                (ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)
                        http.authorizeRequests()
                            .antMatchers("/api/**").authenticated()//谁在前，谁优先级高
                            .antMatchers("/**").permitAll();
        authenticated.and().formLogin().loginPage("/login.html")
                     .and().httpBasic().realmName("ItsNow Platform");
    }
}
