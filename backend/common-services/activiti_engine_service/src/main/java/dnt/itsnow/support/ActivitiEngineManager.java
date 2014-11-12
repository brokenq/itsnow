package dnt.itsnow.support;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.spring.Bean;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * <h1>The Activiti Engine Service Implementations</h1>
 */
@Service
public class ActivitiEngineManager extends Bean implements ActivitiEngineService {

    @Autowired
    ProcessEngine processEngine;

    @Override
    public Deployment deploySingleProcess(String processKey,String processCategory) {
        Deployment deployment = null;
        try {
            //logger.info("process engine:" + processEngine.toString());
            String path = "bpmn/"+processKey+".bpmn20.xml";
            URL url =  this.getClass().getClassLoader().getResource(path);
            assert url != null;
            InputStream is = url.openStream();
            /*DeploymentBuilder db = processEngine.getRepositoryService().createDeployment();
            db.addInputStream(path,is);
            db.name(processKey);
            db.category(processCategory);
            db.deploy();*/
            deployment = processEngine.getRepositoryService().createDeployment()
                    .addInputStream(path, is)
                    .name(processKey)
                    .category(processCategory)
                    .deploy();
            is.close();
            logger.info("Deployed id:"+deployment.getId()+" deploy time:"+deployment.getDeploymentTime()+" deploy name:"+deployment.getName());
        }catch(Exception e){
            logger.error("deploy process error:{}",e.getMessage());
            return null;
        }
        return deployment;
    }

    @Override
    public Deployment deploySingleProcess(InputStream inputStream,String processKey,String processCategory) throws IOException {
        Deployment deployment = null;
        if (inputStream == null) {
            logger.warn("ignore deploy workflow module: {}", processKey);
        } else {
            logger.debug("find workflow module: {}, deploy it!", processKey);
            //ZipInputStream zis = new ZipInputStream(inputStream);
            String path = "bpmn/"+processKey+".bpmn20.xml";
            deployment = processEngine.getRepositoryService().createDeployment()
                    .addInputStream(path, inputStream)
                    .name(processKey)
                    .category(processCategory)
                    .deploy();
            processEngine.getRepositoryService().createDeployment().activateProcessDefinitionsOn(new Date());
            logger.info("deployed id:"+deployment.getId()+" name:"+deployment.getName()+" category:"+deployment.getCategory());
            logger.info("deployed count:"+processEngine.getRepositoryService().createDeploymentQuery().count());

            ProcessDefinition definition = processEngine.getRepositoryService().createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
            InputStream processDiagram = processEngine.getRepositoryService()
                    .getProcessDiagram(definition.getId());
            //FileUtils.copyInputStreamToFile(processDiagram, new File("/diagram.png"));
        }
        return deployment;
    }

    @Override
    public ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId){
        return processEngine.getRepositoryService().createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
    }

    @Override
    public InputStream getImageById(String processKey){
        ProcessDefinition definition = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(processKey).singleResult();
        return processEngine.getRepositoryService().getProcessDiagram(definition.getId());
    }

    @Override
    public long getProcessDefinitionCount(){
        return processEngine.getRepositoryService().createProcessDefinitionQuery().count();
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitions(){
        return processEngine.getRepositoryService().createProcessDefinitionQuery().list();
    }

    @Override
    public long getProcessDeployCount(){
        return processEngine.getRepositoryService().createDeploymentQuery().count();
    }

    @Override
    public List<Deployment> getProcessDeploys(){
        return processEngine.getRepositoryService().createDeploymentQuery().list();
    }

    @Override
    public void deleteProcessDeploy(String id){
        processEngine.getRepositoryService().deleteDeployment(id);
    }

    @Override
    public int deleteAllProcessDeploys(){
        List<Deployment> ls = processEngine.getRepositoryService().createDeploymentQuery().list();
        for(Deployment deployment:ls){
            processEngine.getRepositoryService().deleteDeployment(deployment.getId());
        }
        logger.info("delete all process deploys finish, deleted size:{}",ls.size());
        return ls.size();
    }

    @Override
    public ProcessInstance startProcessInstanceByKey(String processKey,Map<String, Object> variables,String assignee){
        ProcessInstance processInstance = null;
        try{
            processEngine.getIdentityService().setAuthenticatedUserId(assignee);
            processInstance =  processEngine.getRuntimeService().startProcessInstanceByKey(processKey,variables);
        }finally {
            processEngine.getIdentityService().setAuthenticatedUserId(null);
        }
        return processInstance;
    }
    @Override
    public void stopProcessInstanceByKey(String key,String deleteReason){
        List<ProcessInstance> list = this.queryAllInstancesByKey(key);
        for(ProcessInstance instance:list){
            stopProcessInstanceById(instance.getProcessInstanceId(),deleteReason);
        }
    }
    @Override
    public void stopProcessInstanceById(String instanceId,String deleteReason){
        try{
            processEngine.getRuntimeService().deleteProcessInstance(instanceId,deleteReason);
        }catch(ActivitiObjectNotFoundException e){
            logger.error("stop instance:{} error:{}",instanceId,e.getMessage());
        }
    }

    @Override
    public List<ProcessInstance> queryAllInstancesByKey(String processKey){
        return processEngine.getRuntimeService().createProcessInstanceQuery().processDefinitionKey(processKey).list();
    }

    @Override
    public ProcessInstance queryProcessInstanceById(String instanceId){
        return processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
    }

    @Override
    public List<Task> queryTasksByInstanceId(String instanceId){
        return processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
    }

    @Override
    public List<Task> queryTasksAssignee(String userName){
        return processEngine.getTaskService().createTaskQuery().taskAssignee(userName).list();
    }

    @Override
    public List<Task> queryTasksAssignee(String userName,String processKey){
        return processEngine.getTaskService().createTaskQuery().taskAssignee(userName).processDefinitionKey(processKey).list();
    }

    @Override
    public List<Task> queryTasksCandidateUser(String userName){
        return processEngine.getTaskService().createTaskQuery().taskCandidateUser(userName).list();
    }

    @Override
    public List<Task> queryTasksCandidateUser(String userName, String processKey) {
        return processEngine.getTaskService().createTaskQuery().taskCandidateUser(userName).processDefinitionKey(processKey).list();
    }

    @Override
    public List<HistoricProcessInstance> queryTasksFinished(String userName, String processKey){
        return processEngine.getHistoryService().createHistoricProcessInstanceQuery().involvedUser(userName).processDefinitionKey(processKey).finished().orderByProcessInstanceEndTime().desc().list();
    }

    @Override
    public List<HistoricProcessInstance> queryTasksStartedBy(String userName, String processKey){
        return processEngine.getHistoryService().createHistoricProcessInstanceQuery().processDefinitionKey(processKey).startedBy(userName).orderByProcessInstanceStartTime().desc().list();
    }

    @Override
    public List<Task> queryTasksCandidateGroup(String groupName){
        return processEngine.getTaskService().createTaskQuery().taskCandidateGroup(groupName).list();
    }

    @Override
    public Task queryTask(String taskId){
        return processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
    }

    @Override
    public List<IdentityLink> queryTaskIdentity(String taskId){
        List<IdentityLink> identityLinkList = processEngine.getTaskService().getIdentityLinksForTask(taskId);
        for(IdentityLink identityLink:identityLinkList){
            logger.debug("task assignee type:{},userId:{},groupId:{}",identityLink.getType(),identityLink.getUserId(),identityLink.getGroupId());
        }
        return identityLinkList;
    }

    @Override
    public void completeTask(String taskId,Map<String, String> taskVariables,String userId){
        try {
            processEngine.getIdentityService().setAuthenticatedUserId(userId);
            processEngine.getFormService().submitTaskFormData(taskId, taskVariables);
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
     * @param instanceId 流程实例ID
     * @return 封装了各种节点信息
     */
    public List<Map<String, Object>> traceProcess(String instanceId) throws Exception {
        Execution execution = processEngine.getRuntimeService().createExecutionQuery().executionId(instanceId).singleResult();//执行实例
        //Object property = PropertyUtils.getProperty(execution, "activityId");
        if(execution == null)
            return null;
        String activityId = execution.getActivityId();

        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(instanceId)
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

    @Override
    public List<HistoricActivityInstance> traceProcessHistory(String instanceId){

        //HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();

        //List<HistoricDetail> historicDetailList = processEngine.getHistoryService().createHistoricDetailQuery().processInstanceId(instanceId).list();

        //List<HistoricVariableInstance> historicVariableInstanceList = processEngine.getHistoryService().createHistoricVariableInstanceQuery().processInstanceId(instanceId).list();

        return processEngine.getHistoryService().createHistoricActivityInstanceQuery().processInstanceId(instanceId).list();
    }

    @Override
    public void addEventListener(ActivitiEventListener listenerToAdd,ActivitiEventType activitiEventType) {
        processEngine.getRuntimeService().addEventListener(listenerToAdd, activitiEventType);
    }

    @Override
    public void removeEventListener(ActivitiEventListener listenerToRemove) {
        processEngine.getRuntimeService().removeEventListener(listenerToRemove);
    }

    @Override
    public Task queryTask(String instanceId,String activitiId){
        return processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId)
                .taskDefinitionKey(activitiId).singleResult();
    }

    /**
     * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
     *
     * @param activity 活动
     * @param processInstance 实例
     * @param currentActiviti 当前活动
     * @return 活动信息
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
                //setTaskGroup(candidateGroupIdExpressions);

                // 当前处理人
                if (currentTask != null) {
                    //setCurrentTaskAssignee(currentTask);
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
     * @param instance 实例
     * @return 任务信息
     */
    private Task getCurrentTaskInfo(ProcessInstance instance) {
        Task currentTask = null;
        try {
            String activitiId = instance.getActivityId();
            logger.debug("current activity id: {}", activitiId);

            currentTask = processEngine.getTaskService().createTaskQuery().processInstanceId(instance.getId()).taskDefinitionKey(activitiId)
                    .singleResult();
            logger.debug("current task for processInstance: {}", currentTask.toString());

        } catch (Exception e) {
            logger.error("can not get property activityId from processInstance: {}", instance);
        }
        return currentTask;
    }


}
