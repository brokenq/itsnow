package itsnow.dnt.config;

import dnt.itsnow.service.RoleService;
import dnt.itsnow.support.RoleManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>角色管理测试类配置</h1>
 */
@Configuration
public class RoleManagerConfig extends RoleRepositoryConfig {

    @Bean
    public RoleService roleSerivce(){
        return new RoleManager();
    }

}
