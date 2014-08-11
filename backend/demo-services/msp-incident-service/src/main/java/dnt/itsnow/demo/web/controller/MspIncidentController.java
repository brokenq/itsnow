package dnt.itsnow.demo.web.controller;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.demo.model.MspIncident;
import dnt.itsnow.demo.support.MspIncidentManager;
import dnt.itsnow.web.controller.SessionSupportController;
import dnt.util.StringUtils;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * <h1>MSU Incident服务的控制器</h1>
 * <pre>
 * <b>HTTP     URI                                方法            含义  </b>
 * # GET      /api/mspIncidents/                      index           列出所有当前用户的故障单列表，支持过滤，分页，排序等
 * # POST     /api/mspIncidents/start                start           启动故障流程实例
 * # GET      /api/mspIncidents/{instanceId}         query           查询实例ID为{instanceId}的流程的状态，返回task信息
 * # PUT      /api/mspIncidents/{taskId}/complete    complete        完成流程task
 * </pre>
 */
@RestController
@RequestMapping("/api/mspIncidents")
public class MspIncidentController extends SessionSupportController<MspIncident> {

    @Autowired
    ActivitiEngineService activitiEngineService;

    @Autowired
    MspIncidentManager incidentManager;

    private static final String PROCESS_KEY = "msp_incident";

    /**
     * <h2>查询所有当前用户的故障单列表</h2>
     * <p/>
     * GET /api/mspIncidents
     *
     * @return  Incident列表
     */
    @RequestMapping(value = "")
    @ResponseBody
    public List<MspIncident> index(@RequestParam(value = "key", required = false) String key) {

        Set<Task> tasks = new HashSet<Task>();
        //查询分配给当前用户的任务列表
        tasks.addAll(activitiEngineService.queryTasksAssignee(currentUser.getUsername(),PROCESS_KEY));
        //查询当前用户参与的任务列表
        tasks.addAll(activitiEngineService.queryTasksCandidateUser(currentUser.getUsername(),PROCESS_KEY));
        //获取任务所属实例ID列表
        List<String> ids = new ArrayList<String>();
        for(Task task:tasks){
            ids.add(task.getProcessInstanceId());
        }
        logger.debug("instance ids:"+ids.toString());
        //根据实例查询对应表单数据
        indexPage = incidentManager.findByInstanceIds(ids,key,pageRequest);
        return indexPage.getContent();
    }

    /**
     * <h2>查询流程实例ID对应的故障单信息</h2>
     * <p/>
     * GET /api/mspIncidents/{instanceId}
     * @param withHistory 是否返回历史信息
     * @param withIncident 是否返回故障单信息
     * @return 当前的task列表以及历史信息和故障单信息
     */
    @RequestMapping(value = "/{instanceId}")
    @ResponseBody
    public Map<String,Object> query(@PathVariable("instanceId") String instanceId,
                                    @RequestParam(value = "withHistory", required = false) boolean withHistory,
                                    @RequestParam(value = "withIncident", required = false) boolean withIncident) {
        Map<String,Object> map = new HashMap<String, Object>();

        if(withIncident) {
            //获取故障单信息
            MspIncident incident = incidentManager.findByInstanceId(instanceId);
            map.put("incident", incident);
        }
        //获取当前task列表信息
        List<Task> tasks = activitiEngineService.queryTasksByInstanceId(instanceId);
        List<Map<String,String>> ls = new ArrayList<Map<String, String>>();
        for(Task task:tasks) {
            Map<String,String> m = new HashMap<String, String>();
            m.put("taskId",task.getId());
            m.put("taskName",task.getName());
            m.put("taskDesc",task.getDescription());
            m.put("taskAssignee",task.getAssignee());
            ls.add(m);
        }
        map.put("tasks",ls);

        if(withHistory){
            //获取历史信息
            Map<String,Object> hisMap = activitiEngineService.traceProcessHistory(instanceId);
            map.put("history",hisMap);
        }
        return map;
    }

    /**
     * <h2>启动MSP Incident流程实例</h2>
     * <p/>
     * POST /api/mspIncidents/start
     *
     * @return 创建之后的流程实例信息
     */
    @RequestMapping(value = "/start",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> start(HttpServletRequest request,@RequestBody @Valid MspIncident incident){
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(request.getParameterMap());
        variables.put("description",incident.getRequestDescription());
        //start incident process
        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(PROCESS_KEY,variables,currentUser.getUsername());

        //save incident object and persist it
        incident.setCreatedBy(currentUser.getUsername());
        incident.setInstanceId(processInstance.getProcessInstanceId());
        incidentManager.newIncident(incident);

        //return map
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("instanceId",processInstance.getId());
        result.put("activityId",processInstance.getActivityId());
        result.put("defineId",processInstance.getProcessDefinitionId());
        result.put("incident",incident);

        return result;
    }

    /**
     * <h2>完成流程中的task，提交表单数据</h2>
     * <p/>
     * PUT /api/mspIncidents/{taskId}/complete
     * @param taskId    任务ID
     * @return result:true/false
     */
    @RequestMapping(value = "/{taskId}/complete",method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> complete(@PathVariable("taskId") String taskId,HttpServletRequest request,@RequestBody @Valid MspIncident incident){
        Map<String,Object> result = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(currentUser.getUsername())){
            Map<String, String> taskVariables = new HashMap<String, String>();
            // 从request中读取参数然后转换
            Map<String, String[]> parameterMap = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();
            for (Map.Entry<String, String[]> entry : entrySet) {
                taskVariables.put(entry.getKey(),entry.getValue()[0]);
            }

            //todo add transaction process
            // update incident object status and persist it
            incidentManager.updateIncident(incident);
            //claim task if not claim
            activitiEngineService.claimTask(taskId,currentUser.getUsername());
            //complete task
            activitiEngineService.completeTask(taskId,taskVariables,currentUser.getUsername());

            result.put("result","true");
        }
        else{
            result.put("result","false");
        }

        return result;
    }

}
