package dnt.itsnow.support;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.model.Incident;
import dnt.itsnow.model.IncidentStatus;
import dnt.itsnow.model.MsuIncident;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.MsuIncidentRepository;
import dnt.itsnow.service.MsuIncidentService;
import dnt.messaging.MessageBus;
import dnt.spring.Bean;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
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
    private static String mspSn = "msp_001";

    public static final String ROLE_LINE_ONE = "ROLE_LINE_ONE";
    public static final String ROLE_LINE_TWO = "ROLE_LINE_TWO";
    public static final String CAN_PROCESS = "canProcess";
    public static final String HARDWARE_ERROR = "hardwareError";
    public static final String RESOLVED = "resolved";

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Autowired
    MsuIncidentRepository repository;

    @Autowired
    ActivitiEngineService activitiEngineService;

    @Autowired
    MsuEventListener msuEventListener;

    @Autowired
    MessageBus messageBus;

    private ResourceLoader resourceLoader;

    public static String getSendChannel() {
        if(mspSn == null){
            //Todo find mspSn from Contract
        }
        return appSn + "-TO-"+mspSn;
    }

    public static String getListenChannel() {
        return  appSn + "-LISTENER";
    }

    /**
     * <h2>初始化动作，添加事件监听器以及注册消息通道</h2>
     */
    @Override
    protected void performStart() {
        try{
            this.autoDeployment();
        }catch(IOException e){
            logger.error("Auto deploy error:{}",e.getMessage());
        }
        activitiEngineService.addEventListener(msuEventListener, ActivitiEventType.ACTIVITY_COMPLETED);
        //add message-bus listener
        messageBus.subscribe(LISTENER, getListenChannel(), msuEventListener);
    }

    /**
     * <h2>销毁前动作，注销消息通道</h2>
     */
    @Override
    protected void performStop() {
        activitiEngineService.removeEventListener(msuEventListener);
        messageBus.unsubscribe(LISTENER);
    }

    /**
     * <h2>自动部署流程</h2>
     */
    private void autoDeployment() throws IOException {
        String path = "bpmn/"+PROCESS_KEY+".bpmn20.xml";
        InputStream is = null;
        try {
            URL url = this.resourceLoader.getResource(path).getURL();
            assert url != null;
            is = url.openStream();
            activitiEngineService.deploySingleProcess(is,PROCESS_KEY,PROCESS_KEY);

        }catch(Exception e){
            logger.warn("Error deploy process:{} {}",PROCESS_KEY,e.getMessage());
        }finally{
            if(is != null)
                is.close();
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
        logger.debug("instance ids:{}",ids.toString());
        int total = ids.size();
        if(total > 0){
            if(keyword == null)
                keyword = "";
            List<Incident> incidents = repository.findAllByInstanceIds(ids,keyword,pageable);
            return new DefaultPage<Incident>(incidents,pageable,total);
        }else {
            List<Incident> incidents = new ArrayList<Incident>();
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

        //查询当前用户已完成的流程列表
        List<HistoricProcessInstance> historicProcessInstanceList =  activitiEngineService.queryTasksFinished(username, PROCESS_KEY);

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
            return new DefaultPage<Incident>(incidents,pageable,total);
        }else {
            List<Incident> incidents = new ArrayList<Incident>();
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
        MsuIncident msuIncident = new MsuIncident();

        //start incident process
        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(PROCESS_KEY, null, username);

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
        return msuIncident;
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

        msuIncident.setIncident(incident);
        msuIncident.setResult("success");
        return msuIncident;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
