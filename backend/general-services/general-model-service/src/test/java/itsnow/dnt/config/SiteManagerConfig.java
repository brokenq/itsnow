package itsnow.dnt.config;

import dnt.itsnow.platform.service.AutoNumberService;
import dnt.itsnow.platform.support.AutoNumberInMemory;
import dnt.itsnow.service.DepartmentService;
import dnt.itsnow.service.DictionaryService;
import dnt.itsnow.service.SiteService;
import dnt.itsnow.service.WorkTimeService;
import dnt.itsnow.support.DepartmentManager;
import dnt.itsnow.support.DictionaryManager;
import dnt.itsnow.support.SiteManager;
import dnt.itsnow.support.WorkTimeManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Class Usage</h1>
 */
@Configuration
public class SiteManagerConfig extends SiteRepositoryConfig {

    @Bean
    public SiteService siteSerivce(){
        return new SiteManager();
    }

    @Bean
    public DictionaryService processDictionarySerivce(){
        return new DictionaryManager();
    }

    @Bean
    public DepartmentService departmentSerivce(){
        return new DepartmentManager();
    }

    @Bean
    public WorkTimeService workTimeSerivce(){
        return new WorkTimeManager();
    }

    @Bean
    public AutoNumberService autoNumberService(){
        return new AutoNumberInMemory();
    }
}
