/**
 * xiongjie on 14-7-23.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Group;
import dnt.itsnow.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>The Groups Controller</h1>
 * TODO 添加测试用例
 */
@RestController
@RequestMapping("/api/groups")
public class GroupsController extends SessionSupportController<Group> {
    @Autowired
    GroupService groupService;

    // 虽然也是index，但这里不需要分页，application controller 也不会错
    @RequestMapping
    public List<Group> index(@RequestParam("q") String q){
        return groupService.search(q);
    }

    @RequestMapping("/{name}")
    public Group group(@PathVariable("name") String name){
        return groupService.find(name);
    }
}
