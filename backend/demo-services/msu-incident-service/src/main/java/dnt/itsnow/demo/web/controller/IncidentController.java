package dnt.itsnow.demo.web.controller;

import dnt.itsnow.demo.model.Incident;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.demo.support.IncidentManager;
import dnt.util.StringUtils;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * The Incident Process controller
 */
@RestController
@RequestMapping("/api/incidents")
public class IncidentController extends ApplicationController {

    @Autowired
    ActivitiEngineService activitiEngineService;

    @Autowired
    IncidentManager incidentManager;

    @RequestMapping(value = "/start")
    @ResponseBody
    public Object startProcessInstance(@RequestParam("userId") String userId,HttpServletRequest request){
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(request.getParameterMap());

        //start incident process
        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey("msu_incident",variables,userId);

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

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Object completeTasksByUser(@PathVariable("id") String taskId,@RequestParam("userId") String userId,HttpServletRequest request){
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

    @RequestMapping(value = "/{id}/claim")
    @ResponseBody
    public Object claimTasksByUser(@PathVariable("id") String taskId,@RequestParam("userId") String userId,HttpServletRequest request){
        Task task = null;
        if(StringUtils.isNotEmpty(userId)) {
            task = activitiEngineService.claimTask(taskId, userId);
        }

        //update incident object status and persist it.
        return "result:success";
    }
}
