package itsnow.dnt.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.SiteService;
import net.happyonroad.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.SitesController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SitesControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public SiteService siteSerivce(){
        return EasyMock.createMock(SiteService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public SitesController sitesController(){
        return new SitesController();
    }

}
