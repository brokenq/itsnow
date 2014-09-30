/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.GeneralGroupService;
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
 *  GET      /api/groups/{name}      show      列出特定的流程字典记录
 *  POST     /api/groups/          create    创建一个流程字典
 *  PUT      /api/groups/{name}      update    修改一个指定的流程字典
 *  DELETE   /api/groups/{name}      delete    删除指定的流程字典记录
 * </pre>
 */
@RestController
@RequestMapping("/api/groups")
public class GeneralGroupsController extends SessionSupportController<Group> {

    @Autowired
    private GeneralGroupService service;

    private Group group;

    @RequestMapping
    public List<Group> index(@RequestParam(value = "keyword", required = false) String keyword) {
        logger.debug("Listing group keyword {}" + keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed group number {}", indexPage.getNumber());
        return indexPage.getContent();
    }

    @RequestMapping(value="{name}", method = RequestMethod.GET)
    public List<Group> show(@PathVariable("name") String name) {
        logger.debug("Listing group groupName:{}" + name);

        indexPage = service.findAllRelevantInfo(name, pageRequest);

        logger.debug("Listed group size {}", indexPage.getContent().size());
        return indexPage.getContent();
    }

    /**
     * <h2>创建一个角色</h2>
     *
     * POST /api/groups
     *
     * @return 新建的角色
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
     * <h2>更新一个角色</h2>
     *
     * PUT /api/groups/{name}
     *
     * @return 被更新的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.PUT)
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
     * <h2>删除一个角色</h2>
     *
     * DELETE /api/groups/{name}
     *
     * @return 被删除的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
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

    @BeforeFilter({"update", "destroy"})
    public void initGroup(@PathVariable("name") String name){

        this.group = service.findByName(name);//find it by name
    }
}
