package dnt.itsnow.web.controller;

import dnt.itsnow.exception.RoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>MSC角色的控制器</h1>
 * <pre>
 * <b>HTTP    URI                                                            方法      含义  </b>
 *  GET      /api/roles?sort={string}&page={int}&keyword={String}&size={int} index     列出所有的角色，支持过滤、分页、排序
 *  GET      /api/roles/{name}                                               show      列出特定的角色记录
 *  POST     /api/roles                                                      create    创建一个角色
 *  PUT      /api/roles/{name}                                               update    修改一个指定的角色
 *  DELETE   /api/roles/{name}                                               delete    删除指定的角色记录
 * </pre>
 */
@RestController
@RequestMapping("/api/roles")
public class RolesController extends SessionSupportController<Role> {

    @Autowired
    private RoleService service;

    private Role role;

    /**
     * <h2>获得所有的角色</h2>
     * <p/>
     * GET /api/roles
     * @param keyword 查询关键字
     * @return 角色列表
     */
    @RequestMapping
    public List<Role> index(@RequestParam(value = "keyword", required = false) String keyword) {

        logger.debug("Listing role keyword:{}" + keyword);

        indexPage = service.findAll(mainAccount.getId(), keyword, pageRequest);

        logger.debug("Listed role number:{}", indexPage.getContent().size());

        return indexPage.getContent();
    }

    /**
     * <h2>查看一个角色</h2>
     * <p/>
     * GET /api/roles/{name}
     * @param name 角色名称
     * @return 角色实体类
     */
    @RequestMapping(value="{name}", method = RequestMethod.GET)
    public List<Role> show(@PathVariable("name") String name) {

        logger.debug("Listing role name:{}" + name);

        indexPage = service.findAllRelevantInfo(name, pageRequest);

        logger.debug("Listed role size:{}", indexPage.getContent().size());

        return indexPage.getContent();
    }

    /**
     * <h2>创建一个角色</h2>
     * <p/>
     * POST /api/roles
     * @param role 角色实体类
     * @return 角色实体类
     */
    @RequestMapping(method = RequestMethod.POST)
    public Role create(@Valid @RequestBody Role role) {

        logger.info("Creating {}", role);

        try {
            role = service.create(role);
        } catch (RoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created  {}", role);

        return role;
    }

    /**
     * <h2>更新一个角色</h2>
     * <p/>
     * PUT /api/roles/{name}
     * @param role 待更新的角色
     * @return 更新后的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.PUT)
    public Role update(@Valid @RequestBody Role role) {

        logger.info("Updating {}", role);

        this.role.apply(role);
        try {
            role = service.update(role);
        } catch (RoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated {}", role);

        return role;
    }

    /**
     * <h2>删除一个角色</h2>
     * <p/>
     * DELETE /api/roles/{name}
     *
     * @return 被删除的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public Role destroy() {

        logger.warn("Deleting {}", role);

        try {
            service.destroy(role);
        } catch (RoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted  {}", role);

        return role;
    }

    @BeforeFilter({"update", "destroy"})
    public void initRole(@PathVariable("name") String name) {
        this.role = service.findByName(name);//find it by name
    }

}
