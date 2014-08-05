package dnt.itsnow.demo.web.controller;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.demo.model.Incident;
import dnt.itsnow.demo.support.IncidentManager;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.util.StringUtils;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <h1>MSU Incident服务的控制器</h1>
 * <pre>
 * <b>HTTP     URI                                方法            含义  </b>
 * # GET      /api/incidents                      index           列出所有当前用户的故障单列表，支持过滤，分页，排序等
 * # GET      /api/incidents/assignee             indexAssignee   列出分配给当前用户的故障单列表
 * # GET      /api/incidents/candidate            indexCandidate  列出分配给当前用户所属组的故障单列表
 * # POST     /api/incidents/start                start           启动故障流程实例
 * # GET      /api/incidents/{instanceId}         query           查询实例ID为{instanceId}的流程的状态，返回task信息
 * # PUT      /api/incidents/{taskID}/claim       claim           签收task
 * # PUT      /api/incidents/{taskId}/complete    complete        完成流程task
 * </pre>
 */
@RestController
@RequestMapping("/api/incidents")
public class IncidentController extends ApplicationController<Incident> {

    @Autowired
    ActivitiEngineService activitiEngineService;

    @Autowired
    IncidentManager incidentManager;

    private static final String PROCESS_KEY = "msu_incident";


    /**
     * <h2>查询所有当前用户的故障单列表</h2>
     * <p/>
     * GET /api/incidents
     *
     * @return  Incident列表
     */
    @RequestMapping(value = "/")
    @ResponseBody
    public List<Incident> index(@RequestParam(required = false, defaultValue = "") String keyword) {
        String userId = "admin";
        Set<Task> tasks = new HashSet<Task>();
        tasks.addAll(activitiEngineService.queryTasksAssignee(userId,PROCESS_KEY));
        tasks.addAll(activitiEngineService.queryTasksCandidateUser(userId,PROCESS_KEY));
        Set<String> ids = new HashSet<String>();
        for(Task task:tasks){
            ids.add(task.getProcessInstanceId());
        }
        logger.debug("instance ids:"+ids.toString());
        indexPage = incidentManager.findByInstanceIds(ids,pageRequest);
        return indexPage.getContent();
    }

    /**
     * <h2>查询所有分配给当前用户的流程实例列表，用于完成task</h2>
     * <p/>
     * GET /api/incidents/assignee
     *
     * @return  待办Incident列表
     */
    @RequestMapping(value = "/assignee")
    @ResponseBody
    public List<Incident> indexAssignee(HttpServletRequest request) {
        //查询流程引擎中task列表
        String userId = "admin";
        List<Task> tasks = activitiEngineService.queryTasksAssignee(userId,PROCESS_KEY);

        //根据task对应的instanceId，查询对应Incident列表
        for(Task task:tasks){
            task.getProcessInstanceId();
        }

        return null;
    }

    /**
     * <h2>查询所有分配给当前用户所属组的流程实例列表，用于签收使用</h2>
     * <p/>
     * GET /api/incidents/candidate
     *
     * @return  待签收Incident列表
     */
    @RequestMapping(value = "/candidate")
    @ResponseBody
    public List<Incident> indexCandidate(HttpServletRequest request) {
        return null;
    }

    /**
     * <h2>查询流程实例ID信息</h2>
     * <p/>
     * GET /api/incidents/{instanceId}
     *
     * @return
     */
    @RequestMapping(value = "/{instanceId}")
    @ResponseBody
    public Object query(@PathVariable("instanceId") String instanceId) {
        return null;
    }

    /**
     * <h2>启动MSU Incident流程实例</h2>
     * <p/>
     * POST /api/incidents/start
     *
     * @return 创建之后的流程实例信息
     */
    @RequestMapping(value = "/start"/*,method = RequestMethod.POST*/)
    @ResponseBody
    public Object start(@RequestParam("userId") String userId,HttpServletRequest request){
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(request.getParameterMap());

        //start incident process
        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(PROCESS_KEY,variables,userId);

        //save incident object and persist it
        Incident incident = new Incident();
        incident.setCreatedBy(userId);
        incident.setRequestDescription(request.getParameter("description"));
        incident.setInstanceId(processInstance.getProcessInstanceId());
        incidentManager.newIncident(incident);

        List<Map<String, Object>> l = null;
        try {
             l = activitiEngineService.traceProcess(processInstance.getProcessInstanceId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String,Object> result = new HashMap<String, Object>();
        result.put("instanceId",processInstance.getId());
        result.put("activityId",processInstance.getActivityId());
        result.put("defineId",processInstance.getProcessDefinitionId());

        return result;
    }

    /**
     * <h2>完成流程中的task，提交表单数据</h2>
     * <p/>
     * PUT /api/incidents/{taskId}/complete
     * @param taskId    任务ID
     * @return
     */
    @RequestMapping(value = "/{taskId}/complete",method = RequestMethod.PUT)
    @ResponseBody
    public Object complete(@PathVariable("taskId") String taskId,@RequestParam("userId") String userId,HttpServletRequest request){
        List<Map<String, Object>> resultLs = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(userId)){
            Map<String, String> taskVariables = new HashMap<String, String>();
            // 从request中读取参数然后转换
            Map<String, String[]> parameterMap = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();
            for (Map.Entry<String, String[]> entry : entrySet) {
                //String key = entry.getKey();
                taskVariables.put(entry.getKey(),entry.getValue()[0]);
                //ormProperties.put(key.split("_")[1], entry.getValue()[0]);

            }

            //taskVariables.putAll(request.getParameterMap());

            // update incident object status and persist it

            //complete task
            activitiEngineService.completeTask(taskId,taskVariables,userId);
        }

        return resultLs;
    }

    /**
     * <h2>签收task</h2>
     * <p/>
     * PUT /api/incidents/{taskId}/claim
     * @param taskId    任务ID
     * @return
     */
    @RequestMapping(value = "/{taskId}/claim",method = RequestMethod.PUT)
    @ResponseBody
    public Object claimTasksByUser(@PathVariable("taskId") String taskId,@RequestParam("userId") String userId,HttpServletRequest request){
        Task task = null;
        if(StringUtils.isNotEmpty(userId)) {
            task = activitiEngineService.claimTask(taskId, userId);
        }

        //update incident object status and persist it.
        return "result:success";
    }

}
