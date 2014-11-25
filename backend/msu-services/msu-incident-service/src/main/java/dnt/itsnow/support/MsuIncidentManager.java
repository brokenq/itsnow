package dnt.itsnow.support;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.model.Dictionary;
import dnt.itsnow.model.*;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.MsuIncidentRepository;
import dnt.itsnow.service.*;
import dnt.messaging.MessageBus;
import dnt.spring.Bean;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MsuIncidentManager extends Bean implements MsuIncidentService,ResourceLoaderAware {

    public static final String PROCESS_KEY = "msu_incident";
    private static final String LISTENER = "listener";
    private static String appSn = System.getProperty("app.id");
    //private static String appSn = "msu_001";
    //find mspSn by contract and type
    private static String mspSn = null;

    public static final String ROLE_LINE_ONE = "ROLE_LINE_ONE";
    public static final String ROLE_LINE_TWO = "ROLE_LINE_TWO";
    public static final String CAN_PROCESS = "canProcess";
    public static final String HARDWARE_ERROR = "hardwareError";
    public static final String RESOLVED = "resolved";
    public static final String SECONDLINE_CALL_MSP ="secondline-call-msp";

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Autowired
    MsuIncidentRepository repository;

    @Autowired
    ActivitiEngineService activitiEngineService;

    @Autowired
    MsuEventListener msuEventListener;

    @Autowired
    MessageBus messageBus;

    @Autowired
    WorkflowService workflowService;

    @Autowired
    CommonServiceItemService serviceItemService;

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    CommonContractService contractService;

    @Autowired
    CommonAccountService accountService;

    private ResourceLoader resourceLoader;

    public String getSendChannel() {
        if(mspSn == null){
            //find by contract
            try {
                Contract contract = contractService.findBySn("P001");
                Account account = accountService.findById(contract.getMspAccountId());
                mspSn = account.getSn();
                return mspSn + "-LISTENER";
            }catch(Exception e){
                logger.warn(e.getMessage());
                return null;
            }
        }
        return mspSn + "-LISTENER";
    }

    public static String getListenChannel() {
        return  appSn + "-LISTENER";
    }

    /**
     * <h2>初始化动作，添加事件监听器以及注册消息通道</h2>
     */
    @Override
    protected void performStart() {
        logger.debug("Bean starting");
        try{
            this.autoDeployment();
        }catch(IOException e){
            logger.error("Auto deploy error:{}",e.getMessage());
        }
        activitiEngineService.addEventListener(msuEventListener, ActivitiEventType.ACTIVITY_COMPLETED);
        //add message-bus listener
        messageBus.subscribe(LISTENER, getListenChannel(), msuEventListener);
        logger.debug("Bean started");
    }

    /**
     * <h2>销毁前动作，注销消息通道</h2>
     */
    @Override
    protected void performStop() {
        logger.debug("Bean stopping");
        activitiEngineService.removeEventListener(msuEventListener);
        messageBus.unsubscribe(LISTENER);
        logger.debug("Bean stopped");
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
            Dictionary dict = dictionaryService.findByCode("workflow");
            workflow.setDictionary(dict);
            try {
                URL url = this.resourceLoader.getResource(path).getURL();
                assert url != null;
                is = url.openStream();
                workflow = workflowService.create(workflow,is);
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
    public Page<Incident> findAllByUserAndKey(String username, String keyword, Pageable pageable){
        logger.debug("Finding incidents by username:{} and key:{}",username,keyword);
        Set<Task> tasks = new HashSet<Task>();
        //查询分配给当前用户的任务列表
        tasks.addAll(activitiEngineService.queryTasksAssignee(username,PROCESS_KEY));
        //查询当前用户参与的任务列表
        tasks.addAll(activitiEngineService.queryTasksCandidateUser(username,PROCESS_KEY));
        //获取任务所属实例ID列表
        Set<String> ids = new HashSet<String>();
        for(Task task:tasks){
            ids.add(task.getProcessInstanceId());
        }
        logger.debug("instance ids:{}",ids.toString());
        int total = ids.size();
        if(total > 0){
            if(keyword == null)
                keyword = "";
            List<Incident> incidents = repository.findAllByInstanceIds(ids,keyword,pageable);
            logger.debug("Found incidents:{}",total);
            return new DefaultPage<Incident>(incidents,pageable,total);
        }else {
            List<Incident> incidents = new ArrayList<Incident>();
            logger.debug("Not found incidents");
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
    public Page<Incident> findAllClosedByUserAndKey(String username, String keyword, Pageable pageable){
        logger.debug("Finding all closed incidents by user:{} and key:{}",username,keyword);
        //查询当前用户已完成的流程列表
        List<HistoricProcessInstance> historicProcessInstanceList =  activitiEngineService.queryTasksFinished(username, PROCESS_KEY);

        Set<String> ids = new HashSet<String>();
        for(HistoricProcessInstance processInstance:historicProcessInstanceList){
            ids.add(processInstance.getId());
        }
        logger.debug("instance ids：{}",ids.toString());
        int total = ids.size();
        if(total > 0){
            if(keyword == null)
                keyword = "";
            List<Incident> incidents = repository.findAllByInstanceIds(ids,keyword,pageable);
            logger.debug("Fount closed incidents:{}",total);
            return new DefaultPage<Incident>(incidents,pageable,total);
        }else {
            List<Incident> incidents = new ArrayList<Incident>();
            logger.debug("Not found closed incidents");
            return new DefaultPage<Incident>(incidents, pageable, total);
        }
    }

    @Override
    public Page<Incident> findAllCreatedByUserAndKey(String username, String keyword, Pageable pageable) {
        //查询当前用户创建的流程列表
        logger.debug("Finding all incidents createdBy:{}",username);
        List<HistoricProcessInstance> historicProcessInstanceList =  activitiEngineService.queryTasksStartedBy(username, PROCESS_KEY);

        Set<String> ids = new HashSet<String>();
        for(HistoricProcessInstance processInstance:historicProcessInstanceList){
            ids.add(processInstance.getId());
        }
        logger.debug("instance ids：{}",ids.toString());
        int total = ids.size();
        if(total > 0){
            if(keyword == null)
                keyword = "";
            List<Incident> incidents = repository.findAllByInstanceIds(ids,keyword,pageable);
            logger.debug("Found my created incidents:{}",total);
            return new DefaultPage<Incident>(incidents,pageable,total);
        }else {
            List<Incident> incidents = new ArrayList<Incident>();
            logger.debug("Not found my created incidents");
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
    public MsuIncident findByInstanceId(String instanceId,boolean withHistory){
        logger.debug("Finding incident by instanceId:{}",instanceId);
        MsuIncident msuIncident = new MsuIncident();
        Incident incident = repository.findByInstanceId(instanceId);
        msuIncident.setIncident(incident);
        List<Task> tasks = activitiEngineService.queryTasksByInstanceId(instanceId);

        msuIncident.setTasks(tasks);
        if(withHistory){
            //获取历史信息
            List<HistoricActivityInstance> list = activitiEngineService.traceProcessHistory(instanceId);
            msuIncident.setHistoricActivityInstanceList(list);
        }
        logger.debug("Found incident:{}",incident);
        return msuIncident;
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
    public MsuIncident startIncident(String accountName,String username,Incident incident){
        logger.info("Starting msu incident workflow,account:{},user:{}",accountName,username);
        MsuIncident msuIncident = new MsuIncident();

        //start incident process
        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(PROCESS_KEY, null, username);

        //find next tasks
        this.setAssignee(processInstance.getProcessInstanceId(),incident);
        incident.setNumber("INC"+df.format(new Date()));
        incident.setMsuStatus(IncidentStatus.New);
        incident.setCreatedBy(username);
        incident.setUpdatedBy(username);
        incident.setMsuInstanceId(processInstance.getProcessInstanceId());
        incident.setMsuAccountName(accountName);
        incident.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        incident.setUpdatedAt(incident.getCreatedAt());
        repository.create(incident);

        msuIncident.setIncident(incident);
        logger.info("Started msu incident workflow,instanceId:{}",incident.getMsuInstanceId());
        return msuIncident;
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
    public MsuIncident processIncident(String instanceId,String taskId,String username,Incident incident){
        logger.info("Processing msu incident workflow,instance:{},task:{},user:{}",instanceId,taskId,username);
        MsuIncident msuIncident = new MsuIncident();
        incident.setUpdatedBy(username);
        repository.update(incident);

        Map<String, String> taskVariables = new HashMap<String, String>();
        if(incident.getMsuStatus().equals(IncidentStatus.Accepted)){
            if(ROLE_LINE_ONE.equals(incident.getAssignedGroup()))
                taskVariables.put(CAN_PROCESS,incident.getCanProcess()+"");
            else if(ROLE_LINE_TWO.equals(incident.getAssignedGroup()))
                taskVariables.put(HARDWARE_ERROR,incident.getHardwareError()+"");
            else
                logger.debug("incident msu status not match {}",incident);
        }
        else if(incident.getMsuStatus().equals(IncidentStatus.Resolving))
            taskVariables.put(RESOLVED,incident.getResolved()+"");

        activitiEngineService.completeTask(taskId,taskVariables,username);

        Incident incident1 = repository.findByInstanceId(instanceId);
        this.setAssignee(instanceId,incident1);
        repository.update(incident1);
        msuIncident.setIncident(incident1);
        msuIncident.setResult("success");
        logger.info("Processed msu incident workflow");
        return msuIncident;
    }

    @Override
    public InputStream getProcessImage(String instanceId){

        return activitiEngineService.getImageById(PROCESS_KEY,instanceId);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
