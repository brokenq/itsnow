package dnt.itsnow.support;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.model.Incident;
import dnt.itsnow.model.IncidentStatus;
import dnt.itsnow.model.MspIncident;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.MspIncidentRepository;
import dnt.itsnow.service.MspIncidentService;
import dnt.messaging.MessageBus;
import dnt.spring.Bean;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class MspIncidentManager extends Bean implements MspIncidentService {

    @Autowired
    MspIncidentRepository mspIncidentRepository;

    @Autowired
    ActivitiEngineService activitiEngineService;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Autowired
    MspEventListener mspEventListener;

    @Autowired
    MessageBus messageBus;

    public static final String PROCESS_KEY = "msp_incident";

    private static final String LISTENER = "listener";

    public static String getSendChannel() {
        return "MSP-001-TO-MSU-001";
    }

    public static String getListenChannel() {
        return  "MSU-001-TO-MSP-001";
    }

    /**
     * <h2>初始化动作，添加事件监听器以及注册消息通道</h2>
     */
    @Override
    protected void performStart() {
        this.autoDeployment();
        activitiEngineService.addEventListener(mspEventListener, ActivitiEventType.ACTIVITY_COMPLETED);
        //add message-bus listener
        messageBus.subscribe(LISTENER, getListenChannel(), mspEventListener);
    }

    /**
     * <h2>销毁前动作，注销消息通道</h2>
     */
    @Override
    protected void performStop() {
        messageBus.unsubscribe(LISTENER);
    }

    /**
     * <h2>自动部署流程</h2>
     */
    private void autoDeployment() {
        String path = "bpmn/"+PROCESS_KEY+".bpmn20.xml";
        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            assert url != null;
            InputStream is = url.openStream();
            activitiEngineService.deploySingleProcess(is,PROCESS_KEY,PROCESS_KEY);
            is.close();
        }catch(Exception e){
            logger.warn("Error deploy :{}",e);
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
            List<Incident> incidents = mspIncidentRepository.findAllByInstanceIds(ids,keyword,pageable);
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
    public Page<Incident> findClosedByUserAndKey(String username, String keyword, Pageable pageable){

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
            List<Incident> incidents = mspIncidentRepository.findAllByInstanceIds(ids,keyword,pageable);
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
    public MspIncident findByInstanceId(String instanceId,boolean withHistory){
        MspIncident mspIncident = new MspIncident();
        Incident incident = mspIncidentRepository.findByInstanceId(instanceId);
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
        MspIncident mspIncident = new MspIncident();

        //start incident process
        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(PROCESS_KEY, null, username);
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
        mspIncidentRepository.create(incident);

        mspIncident.setIncident(incident);

        return mspIncident;
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
        MspIncident mspIncident = new MspIncident();
        //Task task = activitiEngineService.queryTask(taskId);
        //Incident incident1 = mspIncidentRepository.findByInstanceId(task.getProcessInstanceId());
        incident.setUpdatedBy(username);
        mspIncidentRepository.update(incident);
        Map<String, String> taskVariables = new HashMap<String, String>();
        activitiEngineService.completeTask(taskId,taskVariables,username);
        mspIncident.setIncident(incident);
        mspIncident.setResult("success");
        return mspIncident;
    }

}
