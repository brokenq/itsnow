package dnt.itsnow.web.controller;

import dnt.itsnow.model.Incident;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.support.IncidentManager;
import dnt.util.StringUtils;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Incident Process controller
 */
@RestController
@RequestMapping("/api/workflow/incident")
public class IncidentController extends ApplicationController {

    @Autowired
    ActivitiEngineService activitiEngineService;

    @Autowired
    IncidentManager incidentManager;




    @RequestMapping(value = "/tasks/start")
    @ResponseBody
    public Object startProcessInstance(@RequestParam("userId") String userId,HttpServletRequest request){
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(request.getParameterMap());

        //start incident process
        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey("incident",variables,userId);

        //save incident object and persist it
        Incident incident = new Incident();
        incident.setOwner(userId);
        incident.setTopic(request.getParameter("question"));
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

    @RequestMapping(value = "/tasks/complete/{id}")
    @ResponseBody
    public Object completeTasksByUser(@PathVariable("id") String taskId,@RequestParam("userId") String userId,HttpServletRequest request){
        List<Map<String, Object>> resultLs = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(userId)){
            Map<String, Object> taskVariables = new HashMap<String, Object>();
            taskVariables.putAll(request.getParameterMap());

            // update incident object status and persist it

            //complete task
            activitiEngineService.completeTask(taskId,taskVariables,userId);
        }

        return resultLs;
    }

    @RequestMapping(value = "/tasks/claim/{id}")
    @ResponseBody
    public Object claimTasksByUser(@PathVariable("id") String taskId,@RequestParam("userId") String userId,HttpServletRequest request){
        Task task = null;
        if(StringUtils.isNotEmpty(userId)) {
            task = activitiEngineService.claimTask(taskId, userId);
        }
        return "result:success";
    }
}
