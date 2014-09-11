package itsnow.dnt.config;

import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.DepartmentService;
import dnt.itsnow.test.config.ApplicationControllerConfig;
import dnt.itsnow.web.controller.DepartmentsController;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepartmentsControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public DepartmentService departmentSerivce(){
        return EasyMock.createMock(DepartmentService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    @Bean
    public DepartmentsController departmentsController(){
        return new DepartmentsController();
    }

}
