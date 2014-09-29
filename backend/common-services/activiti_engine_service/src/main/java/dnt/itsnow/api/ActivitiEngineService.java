/**
 * Developer: Kadvin Date: 14-7-10 下午9:06
 */
package dnt.itsnow.api;

import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;

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
     */
    boolean deploySingleProcess(String processKey,String processCategory);

    void deploySingleProcess(InputStream inputStream,String processKey,String processCategory) throws IOException;

    long getProcessDefinitionCount();

    long getProcessDeployCount();

    List<ProcessDefinition> getProcessDefinitions();

    List<Deployment> getProcessDeploys();

    void deleteProcessDeploy(String id);

    int deleteAllProcessDeploys();

    List<Task> queryTasksByInstanceId(String instanceId);

    List<Task> queryTasksAssignee(String userName);

    List<Task> queryTasksAssignee(String userName,String key);

    List<Task> queryTasksCandidateUser(String userName);

    List<Task> queryTasksCandidateUser(String userName,String key);

    List<Task> queryTasksCandidateGroup(String groupName);

    ProcessInstance startProcessInstanceByKey(String key,Map<String, Object> variables,String assignee);

    ProcessInstance queryProcessInstanceById(String instanceId);

    List<HistoricProcessInstance> queryTasksFinished(String userName, String key);

    List<HistoricProcessInstance> queryTasksStartedBy(String userName, String key);

    List<IdentityLink> queryTaskIdentity(String taskId);

    Task queryTask(String taskId);

    Task queryTask(String instanceId,String activitiId);

    Task claimTask(String taskId,String userId);

    void completeTask(String id,Map<String, String> taskVariables,String assignee);

    List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception;

    List<HistoricActivityInstance> traceProcessHistory(String processInstanceId);

    void addEventListener(ActivitiEventListener listenerToAdd);

    void addEventListener(ActivitiEventListener listenerToAdd,ActivitiEventType activitiEventType);

    void removeEventListener(ActivitiEventListener listenerToRemove);
}
