/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>流程字典的控制器</h1>
 * <pre>
 * <b>HTTP    URI                 方法      含义  </b>
 *  GET      /api/groups           index     列出所有的流程字典，并且分页显示
 *  GET      /api/groups/{sn}      show      列出特定的流程字典记录
 *  POST     /api/groups/          create    创建一个流程字典
 *  PUT      /api/groups/{sn}      update    修改一个指定的流程字典
 *  DELETE   /api/groups/{sn}      delete    删除指定的流程字典记录
 * </pre>
 */
//@RestController
//@RequestMapping("/api/groups")
public class GroupsController extends SessionSupportController<Group> {

    @Autowired
    private GroupService service;

    private Group group;

    // 虽然也是index，但这里不需要分页，application controller 也不会错
    @RequestMapping
    public Page<Group> index(@RequestParam(value = "keyword", required = false) String keyword) {
        logger.debug("Listing Groups by keyword: {}" + keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed  {}", indexPage);
        return indexPage;
    }

    @RequestMapping("/{name}")
    public Group group(@PathVariable("name") String name){
        return service.find(name);
    }

    /**
     * <h2>创建一个地点</h2>
     *
     * POST /api/groups
     *
     * @return 新建的地点
     */
    @RequestMapping(method = RequestMethod.POST)
    public Group create(@Valid @RequestBody Group dictionary){
        logger.info("Creating {}", dictionary.getName());

        try {
            dictionary = service.create(dictionary);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", dictionary.getName());
        return dictionary;
    }

    /**
     * <h2>更新一个地点</h2>
     *
     * PUT /api/groups/{sn}
     *
     * @return 被更新的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public Group update(@Valid @RequestBody Group dictionary){

        logger.info("Updateing {}", dictionary.getName());

        if (dictionary == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The group no must be specified");
        }

        this.group.apply(dictionary);
        try {
            dictionary = service.update(dictionary);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", dictionary.getName());

        return dictionary;
    }

    /**
     * <h2>删除一个地点</h2>
     *
     * DELETE /api/groups/{sn}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public Group destroy(){

        if (group == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The group no must be specified");
        }

        try {
            service.destroy(group);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return group;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initGroup(@PathVariable("sn") String sn){

        this.group = service.findBySn(sn);//find it by sn
    }
}
