package dnt.itsnow.support;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.model.*;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.MspIncidentRepository;
import dnt.itsnow.service.CommonServiceItemService;
import dnt.itsnow.service.DictionaryService;
import dnt.itsnow.service.MspIncidentService;
import dnt.itsnow.service.WorkflowService;
import dnt.messaging.MessageBus;
import dnt.spring.Bean;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class MspIncidentManager extends Bean implements MspIncidentService,ResourceLoaderAware {

    public static final  String PROCESS_KEY = "msp_incident";
    private static final String LISTENER    = "listener";
    private static String appSn = System.getProperty("app.id");

    public static final String ROLE_LINE_ONE = "ROLE_LINE_ONE";
    @Autowired
    MspIncidentRepository repository;

    @Autowired
    ActivitiEngineService activitiEngineService;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Autowired
    MspEventListener mspEventListener;

    @Autowired
    @Qualifier("globalMessageBus")
    MessageBus messageBus;

    @Autowired
    WorkflowService workflowService;

    @Autowired
    CommonServiceItemService serviceItemService;

    @Autowired
    DictionaryService dictionaryService;

    private ResourceLoader resourceLoader;

    public static String getAppSn() {
        return appSn;
    }

    public static String getListenChannel() {
        return appSn + "-LISTENER";
    }

    /**
     * <h2>初始化动作，添加事件监听器以及注册消息通道</h2>
     */
    @Override
    protected void performStart() {
        try {
            this.autoDeployment();
        } catch (IOException e) {
            logger.error("Auto deploy error:{}", e.getMessage());
        }
        activitiEngineService.addEventListener(mspEventListener, ActivitiEventType.ACTIVITY_COMPLETED);
        //add message-bus listener
        messageBus.subscribe(LISTENER, getListenChannel(), mspEventListener);
    }

    /**
     * <h2>销毁前动作，注销消息通道</h2>
     */
    @Override
    protected void performStop() {
        activitiEngineService.removeEventListener(mspEventListener);
        messageBus.unsubscribe(LISTENER);
    }

    /**
     * <h2>自动部署流程</h2>
     */
    private void autoDeployment() throws IOException {
        if(workflowService.checkByName("故障流程") == null){
            String path = "bpmn/"+PROCESS_KEY+".bpmn20.xml";
            InputStream is = null;
            Workflow workflow = new Workflow();
            workflow.setName("故障流程");
            workflow.setDescription("初始化故障流程");
            ServiceItem item = serviceItemService.findBySn("SI_3001");
            workflow.setServiceItem(item);
            dnt.itsnow.model.Dictionary dict = dictionaryService.findByCode("120");
            workflow.setDictionary(dict);
            try {
                URL url = this.resourceLoader.getResource(path).getURL();
                assert url != null;
                is = url.openStream();
                workflowService.create(workflow,is);
            }catch(Exception e){
                logger.warn("Error deploy process:{} {}",PROCESS_KEY,e.getMessage());
            }finally{
                if(is != null)
                    is.close();
            }
        }
    }

    /**
     * <h2>根据用户名和流程KEY查询故障列表</h2>
     *
     * @param username  用户名
     * @param keyword  流程定义KEY
     * @param pageable 分页请求
     * @return 故障分页数据
     */
    @Override
    public Page<Incident> findByUserAndKey(String username, String keyword, Pageable pageable){
        logger.debug("finding incident by user:{} and key:{}",username,keyword);
        Set<Task> tasks = new HashSet<Task>();
        //查询分配给当前用户的任务列表
        tasks.addAll(activitiEngineService.queryTasksAssignee(username,PROCESS_KEY));
        //查询当前用户参与的任务列表
        tasks.addAll(activitiEngineService.queryTasksCandidateUser(username,PROCESS_KEY));
        //获取任务所属实例ID列表
        List<String> ids = new ArrayList<String>();
        for(Task task:tasks){
            ids.add(task.getProcessInstanceId());
        }
        logger.debug("instance ids:"+ids.toString());
        int total = ids.size();
        if(total > 0){
            if(keyword == null)
                keyword = "";
            List<Incident> incidents = repository.findAllByInstanceIds(ids,keyword,pageable);
            logger.debug("found incidents:{}",total);
            return new DefaultPage<Incident>(incidents,pageable,total);
        }else {
            List<Incident> incidents = new ArrayList<Incident>();
            logger.debug("found no incident");
            return new DefaultPage<Incident>(incidents, pageable, total);
        }
    }

    @Override
    public Page<Incident> findAllCreatedByUserAndKey(String username, String keyword, Pageable pageable) {
        //查询当前用户创建的流程列表
        logger.debug("Finding all incidents createdBy:{}",username);
        List<HistoricProcessInstance> historicProcessInstanceList =  activitiEngineService.queryTasksStartedBy(username, PROCESS_KEY);

        List<String> ids = new ArrayList<String>();
        for(HistoricProcessInstance processInstance:historicProcessInstanceList){
            ids.add(processInstance.getId());
        }
        logger.debug("instance ids：{}",ids.toString());
        int total = ids.size();
        if(total > 0){
            if(keyword == null)
                keyword = "";
            List<Incident> incidents = repository.findAllByInstanceIds(ids,keyword,pageable);
            logger.debug("found my created incidents:{}",total);
            return new DefaultPage<Incident>(incidents,pageable,total);
        }else {
            List<Incident> incidents = new ArrayList<Incident>();
            logger.debug("found no my created incidents");
            return new DefaultPage<Incident>(incidents, pageable, total);
        }
    }

    /**
     * <h2>根据用户名和流程KEY查询已关闭故障列表</h2>
     *
     * @param username  用户名
     * @param keyword  流程定义KEY
     * @param pageable 分页请求
     * @return 已关闭故障分页数据
     */
    @Override
    public Page<Incident> findClosedByUserAndKey(String username, String keyword, Pageable pageable){
        logger.debug("finding all closed incident by user:{} and key:{}",username,keyword);
        //查询当前用户已完成的流程列表
        List<HistoricProcessInstance> historicProcessInstanceList =  activitiEngineService.queryTasksFinished(username, PROCESS_KEY);

        List<String> ids = new ArrayList<String>();
        for(HistoricProcessInstance processInstance:historicProcessInstanceList){
            ids.add(processInstance.getId());
        }
        logger.debug("instance ids:"+ids.toString());
        int total = ids.size();
        if(total > 0){
            if(keyword == null)
                keyword = "";
            List<Incident> incidents = repository.findAllByInstanceIds(ids,keyword,pageable);
            logger.debug("found closed incidents:{}",total);
            return new DefaultPage<Incident>(incidents,pageable,total);
        }else {
            List<Incident> incidents = new ArrayList<Incident>();
            logger.debug("found no closed incidents");
            return new DefaultPage<Incident>(incidents, pageable, total);
        }
    }

    /**
     * <h2>根据流程实例ID查询故障</h2>
     *
     * @param instanceId  流程实例ID
     * @param withHistory  是否返回历史数据
     * @return 单条故障数据
     */
    @Override
    public MspIncident findByInstanceId(String instanceId,boolean withHistory){
        logger.debug("finding incident by instance:{}",instanceId);
        MspIncident mspIncident = new MspIncident();
        Incident incident = repository.findByInstanceId(instanceId);
        mspIncident.setIncident(incident);
        List<Task> tasks = activitiEngineService.queryTasksByInstanceId(instanceId);

        mspIncident.setTasks(tasks);
        if(withHistory){
            //获取历史信息
            List<HistoricActivityInstance> list = activitiEngineService.traceProcessHistory(instanceId);
            for(HistoricActivityInstance instance:list){

            }
            mspIncident.setHistoricActivityInstanceList(list);
        }
        logger.debug("found incident:{}",incident.getMspInstanceId());
        return mspIncident;
    }

    /**
     * <h2>启动故障流程</h2>
     *
     * @param accountName  帐户名
     * @param username  用户名
     * @param incident  故障表单数据
     * @return 故障表单
     */
    @Override
    public MspIncident startIncident(String accountName,String username,Incident incident){
        logger.info("starting msp incident workflow");
        MspIncident mspIncident = new MspIncident();

        //start incident process
        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(PROCESS_KEY, null, username);
        logger.info("msp incident incident started,instance:{}",processInstance.getProcessInstanceId());
        this.setAssignee(processInstance.getProcessInstanceId(),incident);
        //activitiEngineService.addEventListener( mspEventListener);
        //create incident object and persist it
        incident.setNumber("INC"+df.format(new Date()));
        incident.setMspStatus(IncidentStatus.New);
        incident.setCreatedBy(username);
        incident.setUpdatedBy(username);
        incident.setMspInstanceId(processInstance.getProcessInstanceId());
        incident.setMspAccountName(accountName);
        incident.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setUpdatedAt(incident.getCreatedAt());
        repository.create(incident);
        logger.info("created msp incident object and saved it");
        mspIncident.setIncident(incident);
        logger.info("started msp incident");
        return mspIncident;
    }

    /**
     * 设置处理人/处理组
     * @param instanceId 实例id
     * @param incident 故障对象
     */
    private void setAssignee(String instanceId,Incident incident){
        List<Task> tasks = activitiEngineService.queryTasksByInstanceId(instanceId);
        for(Task task:tasks){
            List<IdentityLink> links = activitiEngineService.queryTaskIdentity(task.getId());
            for(IdentityLink link:links){
                if(link.getGroupId() != null)
                    incident.setAssignedGroup(link.getGroupId());
                if(link.getUserId() != null)
                    incident.setAssignedUser(link.getUserId());
            }
        }
    }

    /**
     * <h2>处理故障流程，可执行的动作包括签收，分析，解决，关闭</h2>
     *
     * @param instanceId  流程实例ID
     * @param taskId    任务ID
     * @param username  用户名
     * @param incident  故障表单数据
     * @return 故障表单数据以及任务信息
     */
    @Override
    public MspIncident processIncident(String instanceId,String taskId,String username,Incident incident){
        logger.info("processing msp incident:{} task:{} by user:{}",instanceId,taskId,username);
        MspIncident mspIncident = new MspIncident();
        //Task task = activitiEngineService.queryTask(taskId);
        //Incident incident1 = repository.findByInstanceId(task.getProcessInstanceId());
        incident.setUpdatedBy(username);
        repository.update(incident);
        Map<String, String> taskVariables = new HashMap<String, String>();
        activitiEngineService.completeTask(taskId,taskVariables,username);
        Incident incident1 = repository.findByInstanceId(instanceId);
        this.setAssignee(instanceId,incident1);
        repository.update(incident1);
        mspIncident.setIncident(incident1);
        mspIncident.setResult("success");
        logger.info("processed msp incident:{} task:{} by user:{}",instanceId,taskId,username);
        return mspIncident;
    }

    @Override
    public InputStream getProcessImage(String instanceId){
        return activitiEngineService.getImageById(PROCESS_KEY,instanceId);
    }

    @Override
    public Page<Incident> findMonitoredByKey(String keyword, PageRequest pageRequest) {
        long total = repository.countMonitoredByKey(keyword);
        if(total > 0){
            if(keyword == null)
                keyword = "";
            List<Incident> incidents = repository.findAllMonitoredByKey(keyword,pageRequest);
            logger.debug("found monitored incidents:{}",total);
            return new DefaultPage<Incident>(incidents,pageRequest,total);
        }else {
            List<Incident> incidents = new ArrayList<Incident>();
            logger.debug("found no monitored incidents");
            return new DefaultPage<Incident>(incidents, pageRequest, total);
        }
    }

    @Override
    public MspIncident grabIncident(String id, User currentUser) {
        Incident incident = repository.findById(id);
        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(MspIncidentManager.PROCESS_KEY, null, currentUser.getUsername());
        logger.info("started msp incident workflow,instance:{}", processInstance.getProcessInstanceId());
        //save incident object and persist it
        incident.setCreatedBy(currentUser.getUsername());
        incident.setMspInstanceId(processInstance.getProcessInstanceId());
        Account account = currentUser.getAccount();
        incident.setMspAccountName(account.getName());
        incident.setNumber("INC" + df.format(new Date()));
        incident.setMspStatus(IncidentStatus.New);
        incident.setAssignedUser(null);
        incident.setAssignedGroup(null);
        incident.setResponseTime(null);
        incident.setResolveTime(null);
        incident.setCloseTime(null);
        incident.setUpdatedBy(currentUser.getUsername());
        incident.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setUpdatedAt(incident.getCreatedAt());
        repository.updateByMsuAccountAndMsuInstanceId(incident);
        MspIncident mspIncident = new MspIncident();
        mspIncident.setResult("success");
        mspIncident.setIncident(incident);
        return mspIncident;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
