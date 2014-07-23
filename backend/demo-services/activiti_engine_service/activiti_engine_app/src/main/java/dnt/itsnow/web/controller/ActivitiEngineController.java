/**
 * Developer: Kadvin Date: 14-7-14 下午5:08
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.services.api.ActivitiEngineService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/test")
    public Object test() {
        activitiEngineService.test("VacationRequest");
        return "true";
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

    @RequestMapping(value = "/upload/{fileName}", method = RequestMethod.POST)
    @ResponseBody
    public void uploadFile(@RequestBody @PathVariable("fileName") String fileName, HttpServletRequest request) {
        try {
            InputStream inputStream = request.getInputStream();
            activitiEngineService.deploySingleProcess(inputStream, fileName);
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

    @RequestMapping(value = "/start/{id}")
    @ResponseBody
    public Object startProcessInstance(@PathVariable("id") String id){
        String key = "vacationRequest";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        ProcessInstance processInstance = activitiEngineService.startProcessInstanceByKey(key,variables);
        Map<String,String> result = new HashMap<String, String>();
        result.put("instanceId",processInstance.getId());
        result.put("activityId",processInstance.getActivityId());
        result.put("defineId",processInstance.getProcessDefinitionId());
        return result;
    }
}
