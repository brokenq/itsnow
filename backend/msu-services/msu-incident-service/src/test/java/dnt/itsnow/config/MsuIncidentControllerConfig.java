package dnt.itsnow.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.MsuIncidentService;
import net.happyonroad.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.MsuIncidentController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>针对 MsuIncident Controller的测试所提供的控制器运行环境</h1>
 */
@Configuration
public class MsuIncidentControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public MsuIncidentService msuIncidentService(){
        return EasyMock.createMock(MsuIncidentService.class);
    }


    //这个也不用scanner，
    //  因为在测试环境下，classpath没有隔离，
    //  会在dnt.itsnow.web.controller包种scan出许多其他控制器实例
    @Bean
    public MsuIncidentController msuIncidentController(){
        return new MsuIncidentController();
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }


}
