package dnt.itsnow.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.DictionaryService;
import net.happyonroad.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.DictionariesController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DictionariesControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public DictionaryService processDictionarySerivce(){
        return EasyMock.createMock(DictionaryService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public DictionariesController processDictionariesController(){
        return new DictionariesController();
    }

}
