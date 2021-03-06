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
 * <h1>The ActivitiEngine Service</h1>
 */
public interface ActivitiEngineService {

    /**
     * 部署单个流程定义
     * @param processKey        流程名称
     * @param processCategory   流程分类
     * @return true/false
     */
    boolean deploySingleProcess(String processKey,String processCategory);

    /**
     * 部署单个流程定义
     * @param inputStream       输入流
     * @param processKey        流程名称
     * @param processCategory   流程分类
     * @throws IOException        I/O异常
     */
    void deploySingleProcess(InputStream inputStream,String processKey,String processCategory) throws IOException;

    /**
     * 获取当前流程定义的数量
     * @return  流程定义的数量
     */
    long getProcessDefinitionCount();

    /**
     * 获取当前已部署的流程数量
     * @return  已部署流程的数量
     */
    long getProcessDeployCount();

    /**
     * 获取所有流程定义列表
     * @return  流程定义列表
     */
    List<ProcessDefinition> getProcessDefinitions();

    /**
     * 获取已部署流程列表
     * @return  已部署流程列表
     */
    List<Deployment> getProcessDeploys();

    /**
     * 根据流程定义ID删除流程定义
     * @param id    流程定义ID
     */
    void deleteProcessDeploy(String id);

    /**
     * 删除所有已部署流程
     * @return 删除的流程数量
     */
    int deleteAllProcessDeploys();

    /**
     * 根据流程实例ID查询流程的任务列表
     * @param instanceId    流程实例ID
     * @return  任务列表
     */
    List<Task> queryTasksByInstanceId(String instanceId);

    /**
     * 查询分配给指定用户的所有流程任务列表
     * @param userName  用户名称
     * @return  任务列表
     */
    List<Task> queryTasksAssignee(String userName);

    /**
     * 查询分配给指定用户的指定流程的任务列表
     * @param userName  用户名称
     * @param key       任务关键字
     * @return          任务列表
     */
    List<Task> queryTasksAssignee(String userName,String key);

    /**
     * 查询指定用户参与的所有流程的任务列表
     * @param userName  用户名称
     * @return          任务列表
     */
    List<Task> queryTasksCandidateUser(String userName);

    /**
     * 查询指定用户参与的指定流程的任务列表
     * @param userName  用户名称
     * @param key        流程关键字
     * @return           任务列表
     */
    List<Task> queryTasksCandidateUser(String userName,String key);

    /**
     * 查询指定用户组参与的所有流程的任务列表
     * @param groupName     组名称
     * @return               任务列表
     */
    List<Task> queryTasksCandidateGroup(String groupName);

    /**
     * 启动流程
     * @param key           流程关键字
     * @param variables     流程参数变量
     * @param assignee      启动流程的用户名称
     * @return              流程实例信息
     */
    ProcessInstance startProcessInstanceByKey(String key,Map<String, Object> variables,String assignee);

    /**
     * 根据流程实例ID查询流程实例信息
     * @param instanceId    流程实例ID
     * @return                流程实例信息
     */
    ProcessInstance queryProcessInstanceById(String instanceId);

    /**
     * 查询已完成的流程列表
     * @param userName  用户名称
     * @param key        流程关键字
     * @return           历史流程列表
     */
    List<HistoricProcessInstance> queryTasksFinished(String userName, String key);

    /**
     * 查询指定用户启动的流程列表
     * @param userName  用户名称
     * @param key       流程关键字
     * @return          历史流程列表
     */
    List<HistoricProcessInstance> queryTasksStartedBy(String userName, String key);

    /**
     * 查询任务的指派信息
     * @param taskId    任务ID
     * @return          IdentityLink列表
     */
    List<IdentityLink> queryTaskIdentity(String taskId);

    /**
     * 查询任务信息
     * @param taskId    任务ID
     * @return           任务信息
     */
    Task queryTask(String taskId);

    /**
     * 查询任务信息
     * @param instanceId    流程实例ID
     * @param activitiId    活动ID
     * @return                任务信息
     */
    Task queryTask(String instanceId,String activitiId);

    /**
     * 完成任务
     * @param id    任务ID
     * @param taskVariables     参数变量
     * @param assignee           完成的用户
     */
    void completeTask(String id,Map<String, String> taskVariables,String assignee);

    /**
     * 追踪流程
     * @param processInstanceId     流程实例ID
     * @return  流程信息
     * @throws Exception
     */
    List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception;

    /**
     * 追踪流程历史记录
     * @param processInstanceId  流程实例ID
     * @return  流程历史活动列表
     */
    List<HistoricActivityInstance> traceProcessHistory(String processInstanceId);

    /**
     * 添加流程事件监听器
     * @param listenerToAdd     监听器
     * @param activitiEventType 事件类型
     */
    void addEventListener(ActivitiEventListener listenerToAdd,ActivitiEventType activitiEventType);

    /**
     * 删除流程事件监听器
     * @param listenerToRemove  监听器
     */
    void removeEventListener(ActivitiEventListener listenerToRemove);
}
