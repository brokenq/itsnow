/**
 * Developer: Kadvin Date: 14-7-10 下午9:06
 */
package dnt.itsnow.services.api;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * The ActivitiEngine Service
 */
public interface ActivitiEngineService {

    /**
     * 部署单个流程定义
     *
     * @param processKey     流程名称
     * @param processCategory 流程分类
     * @throws java.io.IOException 找不到zip文件时
     */
    boolean deploySingleProcess(String processKey,String processCategory);

    void deploySingleProcess(InputStream inputStream,String processKey) throws IOException;

    long getProcessDefinitionCount();

    long getProcessDeployCount();

    List<ProcessDefinition> getProcessDefinitions();

    List<Deployment> getProcessDeploys();

    void deleteProcessDeploy(String id);

    int deleteAllProcessDeploys();

    ProcessInstance startProcessInstanceByKey(String key,Map<String, Object> variables);

    void test(String name);
}
