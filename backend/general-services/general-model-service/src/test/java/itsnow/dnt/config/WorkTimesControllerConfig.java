package itsnow.dnt.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.WorkTimeService;
import net.happyonroad.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.WorkTimesController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkTimesControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public WorkTimeService workTimeSerivce(){
        return EasyMock.createMock(WorkTimeService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public WorkTimesController workTimesController(){
        return new WorkTimesController();
    }

}
