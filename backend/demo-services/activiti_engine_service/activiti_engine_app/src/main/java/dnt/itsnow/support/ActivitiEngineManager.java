package dnt.itsnow.support;

import dnt.itsnow.services.api.ActivitiEngineService;
import dnt.spring.Bean;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

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

    public ProcessInstance startProcessInstanceByKey(String key,Map<String, Object> variables,String assignee){
        ProcessInstance processInstance = null;
        try{
            processEngine.getIdentityService().setAuthenticatedUserId(assignee);
            processInstance =  processEngine.getRuntimeService().startProcessInstanceByKey(key,variables);
        }finally {
            processEngine.getIdentityService().setAuthenticatedUserId(null);
        }
        return processInstance;
    }

    public List<Task> queryTasksByInstanceId(String instanceId){
        return processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
    }

    public List<Task> queryTasksAssignee(String userName){
        return processEngine.getTaskService().createTaskQuery().taskAssignee(userName).list();
    }

    public List<Task> queryTasksCandidateUser(String userName){
        return processEngine.getTaskService().createTaskQuery().taskCandidateUser(userName).list();
    }

    public List<Task> queryTasksCandidateGroup(String groupName){
        return processEngine.getTaskService().createTaskQuery().taskCandidateGroup(groupName).list();
    }

    public Task claimTask(String taskId,String userId){
        processEngine.getTaskService().claim(taskId,userId);
        log.info("task:"+taskId+" has claimed by "+userId);
        Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        return task;
    }

    public void completeTask(String id,Map<String, String> taskVariables,String userId){
        try {
            processEngine.getIdentityService().setAuthenticatedUserId(userId);
            processEngine.getFormService().submitTaskFormData(id, taskVariables);
        } finally {
            processEngine.getIdentityService().setAuthenticatedUserId(null);
        }
        //processEngine.getTaskService().setAssignee(id,userId);
        //processEngine.getTaskService().complete(id,taskVariables);
        //processEngine.getFormService().submitTaskFormData(id,taskVariables);
    }

    /**
     * 流程跟踪图
     *
     * @param processInstanceId 流程实例ID
     * @return 封装了各种节点信息
     */
    public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
        Execution execution = processEngine.getRuntimeService().createExecutionQuery().executionId(processInstanceId).singleResult();//执行实例
        //Object property = PropertyUtils.getProperty(execution, "activityId");
        String activityId = execution.getActivityId();

        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) processEngine.getRepositoryService())
                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
        List<ActivityImpl> activitiList = processDefinition.getActivities();//获得当前任务的所有节点

        List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
        for (ActivityImpl activity : activitiList) {

            boolean currentActiviti = false;
            String id = activity.getId();

            // 当前节点
            if (id.equals(activityId)) {
                currentActiviti = true;
            }

            Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance, currentActiviti);

            activityInfos.add(activityImageInfo);
        }

        return activityInfos;
    }

    /**
     * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
     *
     * @param activity
     * @param processInstance
     * @param currentActiviti
     * @return
     */
    private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
                                                          boolean currentActiviti) throws Exception {
        Map<String, Object> vars = new HashMap<String, Object>();
        Map<String, Object> activityInfo = new HashMap<String, Object>();
        activityInfo.put("currentActiviti", currentActiviti);
        //setPosition(activity, activityInfo);
        //setWidthAndHeight(activity, activityInfo);

        Map<String, Object> properties = activity.getProperties();
        vars.put("任务类型", properties.get("type").toString());

        ActivityBehavior activityBehavior = activity.getActivityBehavior();
        logger.debug("activityBehavior={}", activityBehavior);
        if (activityBehavior instanceof UserTaskActivityBehavior) {

            Task currentTask = null;

			/*
             * 当前节点的task
			 */
            if (currentActiviti) {
                currentTask = getCurrentTaskInfo(processInstance);
            }

			/*
			 * 当前任务的分配角色
			 */
            UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
            TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
            Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
            if (!candidateGroupIdExpressions.isEmpty()) {

                // 任务的处理角色
                setTaskGroup(vars, candidateGroupIdExpressions);

                // 当前处理人
                if (currentTask != null) {
                    setCurrentTaskAssignee(vars, currentTask);
                }
            }
        }

        vars.put("节点说明", properties.get("documentation"));

        String description = activity.getProcessDefinition().getDescription();
        vars.put("描述", description);

        logger.debug("trace variables: {}", vars);
        activityInfo.put("vars", vars);
        return activityInfo;
    }

    /**
     * 获取当前节点信息
     *
     * @param processInstance
     * @return
     */
    private Task getCurrentTaskInfo(ProcessInstance processInstance) {
        Task currentTask = null;
        try {
            String activitiId = processInstance.getActivityId();
            logger.debug("current activity id: {}", activitiId);

            currentTask = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(activitiId)
                    .singleResult();
            logger.debug("current task for processInstance: {}", currentTask.toString());

        } catch (Exception e) {
            logger.error("can not get property activityId from processInstance: {}", processInstance);
        }
        return currentTask;
    }

    private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
        String roles = "";
        for (Expression expression : candidateGroupIdExpressions) {
            String expressionText = expression.getExpressionText();
            String roleName = processEngine.getIdentityService().createGroupQuery().groupId(expressionText).singleResult().getName();
            roles += roleName;
        }
        vars.put("任务所属角色", roles);
    }

    /**
     * 设置当前处理人信息
     *
     * @param vars
     * @param currentTask
     */
    private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
        String assignee = currentTask.getAssignee();
        if (assignee != null) {
            User assigneeUser = processEngine.getIdentityService().createUserQuery().userId(assignee).singleResult();
            String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
            vars.put("当前处理人", userInfo);
        }
    }
}
