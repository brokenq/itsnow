package dnt.itsnow.services.support;

import dnt.itsnow.services.api.ActivitiEngineService;
import dnt.spring.Bean;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * The Activiti Engine Service Implementations
 */
@Service
public class ActivitiEngineManager extends Bean implements ActivitiEngineService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessEngine processEngine;

    public void test(String name){
        try {
            log.info("process engine:" + processEngine.toString());
            String path = "bpmn/"+name+".bpmn20.xml";
            URL url =  this.getClass().getClassLoader().getResource(path);
            InputStream is = null;
            if (url != null) {
                is = url.openStream();
                DeploymentBuilder db = processEngine.getRepositoryService().createDeployment();
                db.addInputStream(path,is);
                db.name(name);
                db.category(name);
                Deployment deployment= db.deploy();
                //Deployment deployment=processEngine.getRepositoryService().createDeployment().addInputStream(name, is).name(name).deploy();
                is.close();
                log.info("Number of process definitions: " + processEngine.getRepositoryService().createProcessDefinitionQuery().count());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ;
    }

    /**
     * 部署单个流程定义
     *
     * @param processKey     流程名称
     * @param processCategory 流程分类
     * @throws java.io.IOException 找不到bpmn20.xml文件时
     */
    public boolean deploySingleProcess(String processKey,String processCategory) {
        try {
            //log.info("process engine:" + processEngine.toString());
            String path = "bpmn/"+processKey+".bpmn20.xml";
            URL url =  this.getClass().getClassLoader().getResource(path);
            InputStream is = url.openStream();
            /*DeploymentBuilder db = processEngine.getRepositoryService().createDeployment();
            db.addInputStream(path,is);
            db.name(processKey);
            db.category(processCategory);
            db.deploy();*/
            Deployment deployment=processEngine.getRepositoryService().createDeployment()
                    .addInputStream(path, is)
                    .name(processKey)
                    .category(processCategory)
                    .deploy();
            is.close();
            log.info("Deploy id:"+deployment.getId()+" deploy time:"+deployment.getDeploymentTime()+" deploy name:"+deployment.getName());
        }catch(Exception e){
            log.warn(e.getMessage());
            return false;
        }
        return true;
    }

    public void deploySingleProcess(InputStream inputStream,String processKey) throws IOException {
        if (inputStream == null) {
            logger.warn("ignore deploy workflow module: {}", processKey);
        } else {
            logger.debug("find workflow module: {}, deploy it!", processKey);
            //ZipInputStream zis = new ZipInputStream(inputStream);
            Deployment deployment = processEngine.getRepositoryService().createDeployment().addInputStream(processKey, inputStream).name(processKey).category(processKey).deploy();
            logger.info("deploy id:"+deployment.getId()+" name:"+deployment.getName()+" category:"+deployment.getCategory());
            logger.info("deploy count:"+processEngine.getRepositoryService().createDeploymentQuery().count());
        }
    }

    public long getProcessDefinitionCount(){
        return processEngine.getRepositoryService().createProcessDefinitionQuery().count();
    }

    public List<ProcessDefinition> getProcessDefinitions(){
        return processEngine.getRepositoryService().createProcessDefinitionQuery().list();
    }

    public long getProcessDeployCount(){
        return processEngine.getRepositoryService().createDeploymentQuery().count();
    }

    public List<Deployment> getProcessDeploys(){
        return processEngine.getRepositoryService().createDeploymentQuery().list();
    }

    public void deleteProcessDeploy(String id){
        processEngine.getRepositoryService().deleteDeployment(id);
    }

    public int deleteAllProcessDeploys(){
        List<Deployment> ls = processEngine.getRepositoryService().createDeploymentQuery().list();
        for(Deployment deployment:ls){
            processEngine.getRepositoryService().deleteDeployment(deployment.getId());
        }
        log.info("delete all process deploys finish, delete size:"+ls.size());
        return ls.size();
    }

    public ProcessInstance startProcessInstanceByKey(String key,Map<String, Object> variables){
        return processEngine.getRuntimeService().startProcessInstanceByKey(key,variables);
    }
}
