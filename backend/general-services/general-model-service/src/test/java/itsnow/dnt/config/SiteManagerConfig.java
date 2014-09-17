package itsnow.dnt.config;

import dnt.itsnow.service.DepartmentService;
import dnt.itsnow.service.ProcessDictionaryService;
import dnt.itsnow.service.SiteService;
import dnt.itsnow.service.WorkTimeService;
import dnt.itsnow.support.DepartmentManager;
import dnt.itsnow.support.ProcessDictionaryManager;
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
    public ProcessDictionaryService processDictionarySerivce(){
        return new ProcessDictionaryManager();
    }

    @Bean
    public DepartmentService departmentSerivce(){
        return new DepartmentManager();
    }

    @Bean
    public WorkTimeService workTimeSerivce(){
        return new WorkTimeManager();
    }
}
