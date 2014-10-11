/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.MscGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>MSC组控制器</h1>
 * <pre>
 * <b>HTTP    URI                 方法      含义  </b>
 *  GET      /api/msc-groups           index     列出所有的组，支持过滤、分页显示
 *  GET      /api/msc-groups/{name}    show      列出特定的组记录
 *  POST     /api/msc-groups           create    创建一个组
 *  PUT      /api/msc-groups/{name}    update    修改一个指定的组
 *  DELETE   /api/msc-groups/{name}    delete    删除指定的组记录
 * </pre>
 */
@RestController
@RequestMapping("/api/msc-groups")
public class MscGroupsController extends SessionSupportController<Group> {

    @Autowired
    private MscGroupService service;

    private Group group;

    /**
     * <h2>可根据关键字查询组信息</h2>
     * GET      /api/msc-groups
     *
     * @param keyword 关键字
     * @return 组信息列表
     */
    @RequestMapping
    public List<Group> index(@RequestParam(value = "keyword", required = false) String keyword) {

        logger.debug("Listing group keyword {}" + keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed group number {}", indexPage.getContent().size());

        return indexPage.getContent();
    }

    /**
     * 展示指定名称的组信息
     * GET      /api/msc-groups/{name}
     *
     * @param name 组名
     * @return 组信息
     */
    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    public List<Group> show(@PathVariable("name") String name) {

        logger.debug("Listing group name:{}", name);

        indexPage = service.findAllRelevantInfo(name, pageRequest);

        logger.debug("Listed group size:{}", indexPage.getContent().size());

        return indexPage.getContent();
    }

    /**
     * <h2>创建一个角色</h2>
     * <p/>
     * POST /api/msc-groups
     *
     * @param group 组实体类
     * @return 新建的角色
     */
    @RequestMapping(method = RequestMethod.POST)
    public Group create(@Valid @RequestBody Group group) {

        logger.info("Creating {}", group);

        try {
            group = service.create(group);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created  {}", group);

        return group;
    }

    /**
     * <h2>更新一个角色</h2>
     * <p/>
     * PUT /api/msc-groups/{name}
     *
     * @param group 组实体类
     * @return 被更新的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.PUT)
    public Group update(@Valid @RequestBody Group group) {

        logger.info("Updating {}", group);

        this.group.apply(group);
        try {
            group = service.update(this.group);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated {}", group.getName());

        return group;
    }

    /**
     * <h2>删除一个角色</h2>
     * <p/>
     * DELETE /api/msc-groups/{name}
     *
     * @return 被删除的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public void destroy() {

        logger.warn("Deleting {}", group);

        try {
            service.destroy(group);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted  {}", group);
    }

    @BeforeFilter({"update", "destroy"})
    public void initGroup(@PathVariable("name") String name) {
        this.group = service.findByName(name);//find it by name
    }

}
