package dnt.itsnow.web.controller;

import dnt.itsnow.exception.MscRoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.model.UserAuthority;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.MscRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>MSC角色的控制器</h1>
 * <pre>
 * <b>HTTP    URI                      方法              含义  </b>
 *  GET      /api/msc_roles            index             列出所有的角色，并且分页显示
 *  GET      /api/msc_roles/{name}     show              列出特定的角色记录
 *  POST     /api/msc_roles            create            创建一个角色
 *  PUT      /api/msc_roles/{name}     update            修改一个指定的角色
 *  DELETE   /api/msc_roles/{name}     delete            删除指定的角色记录
 *  POST     /api/msc_roles/relation   createRelation    创建一个角色与用户关系
 *  DELETE   /api/msc_roles/relation   destroyRelation   删除指定的角色与用户的关系
 * </pre>
 */
@RestController
@RequestMapping("/api/msc-roles")
public class MscRolesController extends SessionSupportController<Role> {

    @Autowired
    private MscRoleService service;

    private Role role;

    /**
     * <h2>获得所有的角色</h2>
     * <p></p>
     * GET /api/msc-roles
     * @param keyword 查询关键字
     * @return 角色列表
     */
    @RequestMapping
    public List<Role> index(@RequestParam(value = "keyword", required = false) String keyword) {

        logger.debug("Listing role keyword:{}" + keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed role number:{}", indexPage.getContent().size());

        return indexPage.getContent();
    }

    /**
     * <h2>查看一个角色</h2>
     * <p/>
     * GET /api/msc-roles/{name}
     * @param name 角色名称
     * @return 角色
     */
    @RequestMapping(value="{name}", method = RequestMethod.GET)
    public List<Role> show(@PathVariable("name") String name) {
        logger.debug("Listing role name:{}" + name);

        indexPage = service.findAllRelevantInfo(name, pageRequest);

        logger.debug("Listed  role size {}", indexPage.getContent().size());
        return indexPage.getContent();
    }

    /**
     * <h2>创建一个角色</h2>
     * <p/>
     * POST /api/msc-roles
     * @param role 角色实体类
     * @return 新建的角色
     */
    @RequestMapping(method = RequestMethod.POST)
    public Role create(@Valid @RequestBody Role role) {
        logger.info("Creating {}", role);

        try {
            role = service.create(role);
        } catch (MscRoleException e) {
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
     * PUT /api/msc-roles/{name}
     * @param role 角色实体类
     * @return 被更新的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.PUT)
    public Role update(@Valid @RequestBody Role role) {

        logger.info("Updating {}", role);

        this.role.apply(role);
        try {
            this.role = service.update(this.role);
        } catch (MscRoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated {}", this.role);

        return this.role;
    }

    /**
     * <h2>删除一个角色</h2>
     * <p/>
     * DELETE /api/msc-roles/{name}
     *
     * @return 被删除的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public void destroy() {

        logger.warn("Deleting {}", role);

        try {
            service.destroy(role);
        } catch (MscRoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted {}", role);
    }

    @RequestMapping(value = "relation", method = RequestMethod.POST)
    public UserAuthority createRelation(@RequestBody UserAuthority userAuthority) {

        logger.info("Creating role and user relation : {}", userAuthority);

        try {
            service.createRoleAndUserRelation(userAuthority);
        } catch (MscRoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created role and user relation : {}", userAuthority);

        return userAuthority;
    }

    @RequestMapping(value = "relation", method = RequestMethod.DELETE)
    public void deleteRelation(@RequestBody UserAuthority userAuthority) {

        logger.info("Deleting role and user relation : {}", userAuthority);

        try {
            service.destroyRoleAndUserRelation(userAuthority);
        } catch (MscRoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Deleted role and user relation : {}", userAuthority);
    }

    @BeforeFilter({"update", "destroy"})
    public void initRole(@PathVariable("name") String name) {
        this.role = service.findByName(name);//find it by name
    }
}
