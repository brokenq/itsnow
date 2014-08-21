/**
 * Developer: Kadvin Date: 14-7-14 下午5:08
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.api.ActivitiEngineService;
import dnt.util.StringUtils;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * The user controller
 */
@RestController
@RequestMapping("/api/workflow")
public class ActivitiEngineController extends ApplicationController {

    @Autowired
    ActivitiEngineService activitiEngineService;

    @RequestMapping(value = "/process-definition-list")
    public List<Map<String, String>> processDefinitionList() {
        List<ProcessDefinition> ls = activitiEngineService.getProcessDefinitions();
        List<Map<String, String>> resultLs = new ArrayList<Map<String, String>>();
        for (ProcessDefinition pd : ls) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("defineId", pd.getId());
            map.put("name", pd.getName());
            map.put("category", pd.getCategory());
            map.put("deployId", pd.getDeploymentId());
            map.put("key", pd.getKey());
            map.put("version", pd.getVersion() + "");
            map.put("resourceName", pd.getResourceName());
            resultLs.add(map);
        }
        return resultLs;
    }

    @RequestMapping(value = "/process-definition-count")
    public long processDefinitionCount() {
        return activitiEngineService.getProcessDefinitionCount();
    }

    @RequestMapping(value = "/process-deployment-list")
    public List<Map<String, String>> processDeployList() {
        List<Deployment> ls = activitiEngineService.getProcessDeploys();
        List<Map<String, String>> resultLs = new ArrayList<Map<String, String>>();
        for (Deployment deploy : ls) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("deployId", deploy.getId());
            map.put("name", deploy.getName());
            map.put("deployTime", deploy.getDeploymentTime().toString());
            map.put("category", deploy.getCategory());
            resultLs.add(map);
        }
        return resultLs;
    }

    @RequestMapping(value = "/process-deployment-count")
    public long processDeployCount() {
        return activitiEngineService.getProcessDeployCount();
    }

    @RequestMapping("/deployment")
    public Object deploy(@RequestParam("key") String key, @RequestParam("category") String category) {
        try {
            //activitiEngineService.test(key);
            activitiEngineService.deploySingleProcess(key, category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "true";
    }

    @RequestMapping(value = "/upload/{filename}", method = RequestMethod.POST)
    @ResponseBody
    public void uploadFile(@RequestBody @PathVariable("filename") String fileName, HttpServletRequest request) {
        try {
            InputStream inputStream = request.getInputStream();
            activitiEngineService.deploySingleProcess(inputStream, fileName,fileName);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/deployment/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") String id) throws Exception {
        activitiEngineService.deleteProcessDeploy(id);
        return "{'result':'true'}";
    }

    @RequestMapping(value = "/deployment", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteAll() throws Exception {
        int size = activitiEngineService.deleteAllProcessDeploys();
        return "{'result':'true','size':" + size + "}";
    }

    @RequestMapping(value = "/tasks")
    @ResponseBody
    public Object queryTasksByInstanceId(@RequestParam("instance-id") String instanceId){
        List<Map<String, Object>> resultLs = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(instanceId)){
            List<Task> ls = activitiEngineService.queryTasksByInstanceId(instanceId);
            for(Task task:ls){
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("taskId",task.getId());
                map.put("instanceId",task.getProcessInstanceId());
                map.put("taskName",task.getName());
                map.put("taskAssignee",task.getAssignee());
                map.put("taskDesc",task.getDescription());
                map.put("taskOwner",task.getOwner());
                //map.put("parentTaskId",task.getParentTaskId());
                //map.put("processVariables",task.getProcessVariables());
                //map.put("localVariables",task.getTaskLocalVariables());
                map.put("createTime",task.getCreateTime());

                resultLs.add(map);
            }
        }

        return resultLs;
    }

    @RequestMapping(value = "/tasks-assignee")
    @ResponseBody
    public Object queryTasksByAssignee(@RequestParam("username") String userName){
        List<Map<String, Object>> resultLs = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(userName)){
            List<Task> ls = activitiEngineService.queryTasksAssignee(userName);
            for(Task task:ls){
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("taskId",task.getId());
                map.put("instanceId",task.getProcessInstanceId());
                map.put("taskName",task.getName());
                map.put("taskAssignee",task.getAssignee());
                map.put("taskDesc",task.getDescription());
                map.put("taskOwner",task.getOwner());
                //map.put("parentTaskId",task.getParentTaskId());
                //map.put("processVariables",task.getProcessVariables());
                //map.put("localVariables",task.getTaskLocalVariables());
                map.put("createTime",task.getCreateTime());

                resultLs.add(map);
            }
        }

        return resultLs;
    }

    @RequestMapping(value = "/tasks-candidate-user")
    @ResponseBody
    public Object queryTasksByCandidateUser(@RequestParam("username") String userName){
        List<Map<String, Object>> resultLs = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(userName)){
            List<Task> ls = activitiEngineService.queryTasksCandidateUser(userName);
            for(Task task:ls){
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("taskId",task.getId());
                map.put("instanceId",task.getProcessInstanceId());
                map.put("taskName",task.getName());
                map.put("taskAssignee",task.getAssignee());
                map.put("taskDesc",task.getDescription());
                map.put("taskOwner",task.getOwner());
                //map.put("parentTaskId",task.getParentTaskId());
                //map.put("processVariables",task.getProcessVariables());
                //map.put("localVariables",task.getTaskLocalVariables());
                map.put("createTime", task.getCreateTime());
                resultLs.add(map);
            }
        }

        return resultLs;
    }

    @RequestMapping(value = "/tasks-candidate-group")
    @ResponseBody
    public Object queryTasksByUser(@RequestParam("group-name") String groupName){
        List<Map<String, Object>> resultLs = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(groupName)){
            List<Task> ls = activitiEngineService.queryTasksCandidateGroup(groupName);
            for(Task task:ls){
                Map<String,Object> map = new HashMap<String, Object>();
                try {
                    activitiEngineService.traceProcess(task.getProcessInstanceId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put("taskId",task.getId());
                map.put("instanceId",task.getProcessInstanceId());
                map.put("taskName",task.getName());
                map.put("taskAssignee",task.getAssignee());
                map.put("taskDesc",task.getDescription());
                map.put("taskOwner",task.getOwner());
                //map.put("parentTaskId",task.getParentTaskId());
                //map.put("processVariables",task.getProcessVariables());
                //map.put("localVariables",task.getTaskLocalVariables());
                map.put("createTime",task.getCreateTime());
                resultLs.add(map);
            }
        }

        return resultLs;
    }

    @RequestMapping(value = "/tasks/start/{key}")
    @ResponseBody
    public Object startProcessInstance(@PathVariable("key") String key,@RequestParam("name") String name,HttpServletRequest request){
        //String key = "vacationRequest";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(request.getParameterMap());
        //variables.put("employeeName", "kermit");
        //variables.put("numberOfDays", new Integer(4));
        //variables.put("vacationMotivation", "I'm really tired!");

        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(key,variables,name);

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
    public Object completeTasksByUser(@PathVariable("id") String taskId,@RequestParam("username") String userName,HttpServletRequest request){
        List<Map<String, Object>> resultLs = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(userName)){
            Map<String, String> taskVariables = new HashMap<String, String>();
            Map<String, String[]> parameterMap = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();
            for (Map.Entry<String, String[]> entry : entrySet) {
                //String key = entry.getKey();
                taskVariables.put(entry.getKey(),entry.getValue()[0]);
                //ormProperties.put(key.split("_")[1], entry.getValue()[0]);

            }
            //taskVariables.putAll(request.getParameterMap());

            activitiEngineService.completeTask(taskId,taskVariables,userName);
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
        return task;
    }
}
