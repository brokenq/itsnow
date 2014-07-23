/**
 * xiongjie on 14-7-23.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Group;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>The Groups Controller</h1>
 */
@RestController
@RequestMapping("/api/groups")
public class GroupsController extends ApplicationController{
    @Autowired
    GroupService groupService;

    @RequestMapping
    public List<Group> groups(@RequestParam("q") String q){
        return groupService.search(q);
    }

    @RequestMapping("/{name}")
    public Group group(@PathVariable("name") String name){
        return groupService.find(name);
    }
}
