package dnt.itsnow.web.controller;

import dnt.itsnow.exception.RoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.model.User;
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
 * <h1>角色的控制器</h1>
 * <pre>
 * <b>HTTP    URI                 方法      含义  </b>
 *  GET      /api/roles           index     列出所有的角色，并且分页显示
 *  GET      /api/roles/{sn}      show      列出特定的角色记录
 *  POST     /api/roles/          create    创建一个角色
 *  PUT      /api/roles/{sn}      update    修改一个指定的角色
 *  DELETE   /api/roles/{sn}      delete    删除指定的角色记录
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
     *
     * @return 角色列表
     */
    @RequestMapping
    public List<Role> index(@RequestParam(value = "keyword", required = false) String keyword) {
        logger.debug("Listing Role keyword:{}" + keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed Role number {}", indexPage.getNumber());
        return indexPage.getContent();
    }

    /**
     * <h2>查看一个角色</h2>
     * <p/>
     * GET /api/roles/{sn}
     *
     * @return 角色
     */
    @RequestMapping("/{sn}")
    public Role show() {
        if (role == null) {
            throw new WebServerSideException(HttpStatus.NO_CONTENT, "The role is empty");
        }
        return role;
    }

    /**
     * <h2>创建一个角色</h2>
     * <p/>
     * POST /api/roles
     *
     * @return 新建的角色
     */
    @RequestMapping(method = RequestMethod.POST)
    public Role create(@Valid @RequestBody Role dictionary) {
        logger.info("Creating {}", dictionary.getName());

        try {
            dictionary = service.create(dictionary);
        } catch (RoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", dictionary.getName());
        return dictionary;
    }

    /**
     * <h2>更新一个角色</h2>
     * <p/>
     * PUT /api/roles/{sn}
     *
     * @return 被更新的角色
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public Role update(@Valid @RequestBody Role dictionary) {

        logger.info("Updateing {}", dictionary.getName());

        if (dictionary == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The role no must be specified");
        }

        this.role.apply(dictionary);
        try {
            dictionary = service.update(dictionary);
        } catch (RoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", dictionary.getName());

        return dictionary;
    }

    /**
     * <h2>删除一个角色</h2>
     * <p/>
     * DELETE /api/roles/{sn}
     *
     * @return 被删除的角色
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public Role destroy() {

        if (role == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The role no must be specified");
        }

        try {
            service.destroy(role);
        } catch (RoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return role;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initRole(@PathVariable("sn") String sn) {

        this.role = service.findBySn(sn);//find it by sn
    }
}
