package dnt.itsnow.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.ProcessDictionarySerivce;
import dnt.itsnow.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.ProcessDictionariesController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessDictionariesControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public ProcessDictionarySerivce processDictionarySerivce(){
        return EasyMock.createMock(ProcessDictionarySerivce.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public ProcessDictionariesController processDictionariesController(){
        return new ProcessDictionariesController();
    }

}
