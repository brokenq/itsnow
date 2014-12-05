/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.config;

import dnt.itsnow.service.*;
import net.happyonroad.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.ItsnowHostsController;
import dnt.itsnow.web.controller.ItsnowProcessesController;
import dnt.itsnow.web.controller.ItsnowSchemasController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>针对 Deploy(hosts, processes) Controller的测试所提供的控制器运行环境</h1>
 */
@Configuration
public class DeployControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public ItsnowHostService itsnowHostService(){
        return EasyMock.createMock(ItsnowHostService.class);
    }

    @Bean
    public ItsnowProcessService itsnowProcessService(){
        return EasyMock.createMock(ItsnowProcessService.class);
    }

    @Bean
    public ItsnowSchemaService itsnowSchemaService(){
        return EasyMock.createMock(ItsnowSchemaService.class);
    }


    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public CommonAccountService commonAccountService(){
        return EasyMock.createMock(CommonAccountService.class);
    }

    //这个也不用scanner，
    //  因为在测试环境下，classpath没有隔离，
    //  会在dnt.itsnow.web.controller包种scan出许多其他控制器实例
    @Bean
    public ItsnowHostsController itsnowHostsController(){
        return new ItsnowHostsController();
    }

    @Bean
    public ItsnowProcessesController itsnowProcessesController(){
        return new ItsnowProcessesController();
    }

    @Bean
    public ItsnowSchemasController itsnowSchemasController(){
        return new ItsnowSchemasController();
    }


}
