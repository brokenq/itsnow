/**
 * Developer: Kadvin Date: 14-7-14 下午5:08
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.api.ActivitiEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The user controller
 */
@RestController
@RequestMapping("/api/activitiengine")
public class ActivitiEngineController extends ApplicationController {

    @Autowired
    ActivitiEngineService activitiEngineService;



    @RequestMapping("{name}")
    public Object find(@PathVariable("name") String name ){
        activitiEngineService.test();
        return "true";
    }
}
