package dnt.itsnow.web.controller;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <h1>用户组的控制器</h1>
 * <pre>
 * <b>HTTP    URI                 方法      含义  </b>
 *  GET      /api/groups          index     列出所有的用户组，并且分页显示
 *  GET      /api/groups/{name}   show      列出特定的用户组记录
 *  POST     /api/groups/         create    创建一个用户组
 *  PUT      /api/groups/{name}   update    修改一个指定的用户组
 *  DELETE   /api/groups/{name}   delete    删除指定的用户组记录
 * </pre>
 */
@RestController
@RequestMapping("/api/groups")
public class GroupsController extends SessionSupportController<Group> {

    @Autowired
    private GroupService groupService;

    private Group group;

    /**
     * <h2>列出所有用户组</h2>
     * <p></p>
     * /api/groups
     *
     * @param keyword 查询关键字
     * @return 用户列表，带分页显示
     */
    @RequestMapping
    public Page<Group> index(@RequestParam(value = "keyword", required = false) String keyword) {

        logger.debug("Listing Groups by keyword: {}", keyword);

        indexPage = groupService.findAll(keyword, pageRequest);

        logger.debug("Listed  {}", indexPage);
        return indexPage;
    }

    /**
     * <h2>查询指定用户组</h2>
     * <p></p>
     * /api/groups/{name}
     *
     * @return 用户组
     */
    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    public Group show() {
        return this.group;
    }

    /**
     * <h2>创建一个用户组</h2>
     * <p/>
     * POST /api/groups
     *
     * @param group 待创建的用户组
     * @return 新建的用户组
     */
    @RequestMapping(method = RequestMethod.POST)
    public Group create(@Valid @RequestBody Group group) {

        logger.info("Creating {}", group);

        try {
            group = groupService.create(group);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created  {}", group);

        return group;
    }

    /**
     * <h2>更新一个用户组</h2>
     * <p/>
     * PUT /api/groups/{name}
     *
     * @param group 待更新的用户组
     * @return 更新后的用户组
     */
    @RequestMapping(value = "{name}", method = RequestMethod.PUT)
    public Group update(@Valid @RequestBody Group group) {

        logger.info("Updating {}", group);

        this.group.apply(group);
        try {
            this.group = groupService.update(this.group);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated  {}", this.group);

        return this.group;
    }

    /**
     * <h2>删除一个用户组</h2>
     * <p/>
     * DELETE /api/groups/{name}
     *
     * @return 被删除的用户组
     */
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public Group destroy() {

        logger.warn("Deleting {}", group);

        try {
            groupService.destroy(group);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted  {}", group);

        return group;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initGroup(@PathVariable("name") String name) {
        this.group = groupService.findByName(name);
    }

}
