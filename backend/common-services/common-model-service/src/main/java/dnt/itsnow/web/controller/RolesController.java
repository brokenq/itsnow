package dnt.itsnow.web.controller;

import dnt.itsnow.exception.RoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
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
 *  GET      /api/roles/users                                                listUsers 列出当前用户所属账户中，所有用户的信息记录
 * </pre>
 */
@RestController
@RequestMapping("/api/roles")
public class RolesController extends SessionSupportController<Role> {

    @Autowired
    private RoleService service;

    @Autowired
    @Qualifier("plainUserService")
    CommonUserService commonUserService;

    private Role role;

    /**
     * <h2>获得所有的角色</h2>
     * <p/>
     * GET /api/roles
     * @param keyword 查询关键字
     * @return 角色列表
     */
    @RequestMapping
    public Page<Role> index(@RequestParam(value = "keyword", required = false) String keyword) {

        logger.debug("Listing Roles by keyword: {}", keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed  {}", indexPage);

        return indexPage;
    }

    /**
     * <h2>查看一个角色</h2>
     * <p/>
     * GET /api/roles/{name}
     *
     * @return 角色实体类
     */
    @RequestMapping(value="{name}", method = RequestMethod.GET)
    public Role show() {
        return this.role;
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
            this.role = service.update(this.role);
        } catch (RoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated  {}", this.role);

        return this.role;
    }

    /**
     * <h2>删除一个角色</h2>
     * <p/>
     * DELETE /api/roles/{name}
     *
     * @return 被删除的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public void destroy() {

        logger.warn("Deleting {}", role);

        try {
            service.destroy(role);
        } catch (RoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted  {}", role);

    }

    /**
     * <h2>列出当前用户所属账户中，所有用户的信息记录</h2>
     * <p></p>
     *
     * GET     /api/roles/users/belongs_to_account
     * @return 用户列表
     */
    @RequestMapping(value = "users/belongs_to_account", method = RequestMethod.GET)
    public List<User> listUsers() {

        logger.info("Listing users by current account:{}", mainAccount);

        List<User> users = commonUserService.findAllByAccountAndContract(mainAccount);

        logger.info("Listed  {}", users);

        return users;
    }

    @RequestMapping(value = "check/{name}", method = RequestMethod.GET)
    public HashMap checkUnique(@PathVariable("name") String name){
        Role role = service.findByName(name);
        if( role != null ){
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate role name: " + role.getName());
        }else{
            return new HashMap();
        }
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initRole(@PathVariable("name") String name) {
        this.role = service.findByName(name);//find it by name
    }

}
