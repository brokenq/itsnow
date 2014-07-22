/**
 * Developer: Kadvin Date: 14-7-10 下午9:07
 */
package dnt.itsnow.services.support;

import dnt.itsnow.services.api.ActivitiEngineService;
import dnt.spring.Bean;
import org.activiti.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Session Service Implementations
 */
@Service
public class ActivitiEngineManager extends Bean implements ActivitiEngineService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessEngine processEngine;

    public void test(){
        try {
            //ProcessEngine processEngine = processEngines.getDefaultProcessEngine();
            log.info("process engine:" + processEngine.toString());

            //URL url = Class.class.getClass().getResource("/activiti/VacationRequest.bpmn20.xml");
            //InputStream is = url.openStream();
            //classpath*:/activiti

            String deploymentId = getRepositoryService()
                    .createDeployment()
                    .addClasspathResource("/dnt/itsnow/services/support/VacationRequest.bpmn20.xml")
                    .deploy()
                    .getId();


            //Deployment deployment=getRepositoryService().createDeployment().addInputStream("bpmn20.xml", is).name("holidayRequest").deploy();
            getRepositoryService().createDeployment()
                    .addClasspathResource("/activiti/VacationRequest.bpmn20.xml")
                    .deploy();
            log.info("Number of process definitions: " + getRepositoryService().createProcessDefinitionQuery().count());
        }catch(Exception e){
            e.printStackTrace();
        }
        return ;
    }


    @Override
    public RuntimeService getRuntimeService() {
        return processEngine.getRuntimeService();
    }

    @Override
    public RepositoryService getRepositoryService() {
        return processEngine.getRepositoryService();
    }

    @Override
    public ManagementService getManagementService() {
        return processEngine.getManagementService();
    }

    @Override
    public TaskService getTaskService() {
        return processEngine.getTaskService();
    }

    @Override
    public IdentityService getIdentityService() {
        return processEngine.getIdentityService();
    }

    @Override
    public HistoryService getHistoryService() {
        return processEngine.getHistoryService();
    }

    @Override
    public FormService getFormService() {
        return processEngine.getFormService();
    }
}
