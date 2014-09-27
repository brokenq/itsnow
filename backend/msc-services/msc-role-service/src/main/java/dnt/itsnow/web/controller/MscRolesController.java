package dnt.itsnow.web.controller;

import dnt.itsnow.exception.MscRoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MscRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>MSC角色的控制器</h1>
 * <pre>
 * <b>HTTP    URI                      方法      含义  </b>
 *  GET      /api/msc-roles            index     列出所有的角色，并且分页显示
 *  GET      /api/msc-roles/{name}     show      列出特定的角色记录
 *  POST     /api/msc-roles/           create    创建一个角色
 *  PUT      /api/msc-roles/{name}     update    修改一个指定的角色
 *  DELETE   /api/msc-roles/{name}     delete    删除指定的角色记录
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
     * <p/>
     * GET /api/msc-roles
     * @param keyword 查询关键字
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
     * GET /api/msc-roles/{name}
     *
     * @return 角色
     */
    @RequestMapping(value="{name}", method = RequestMethod.GET)
    public List<Role> show(@PathVariable("name") String name) {
        logger.debug("Listing group groupName:{}" + name);

        indexPage = service.findAllRelevantInfo(name, pageRequest);

        logger.debug("Listed group size {}", indexPage.getContent().size());
        return indexPage.getContent();
    }

    /**
     * <h2>创建一个角色</h2>
     * <p/>
     * POST /api/msc-roles
     *
     * @return 新建的角色
     */
    @RequestMapping(method = RequestMethod.POST)
    public Role create(@Valid @RequestBody Role dictionary) {
        logger.info("Creating {}", dictionary.getName());

        try {
            dictionary = service.create(dictionary);
        } catch (MscRoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", dictionary.getName());
        return dictionary;
    }

    /**
     * <h2>更新一个角色</h2>
     * <p/>
     * PUT /api/msc-roles/{name}
     *
     * @return 被更新的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.PUT)
    public Role update(@Valid @RequestBody Role dictionary) {

        logger.info("Updateing {}", dictionary.getName());

        if (dictionary == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The role no must be specified");
        }

        this.role.apply(dictionary);
        try {
            dictionary = service.update(dictionary);
        } catch (MscRoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", dictionary.getName());

        return dictionary;
    }

    /**
     * <h2>删除一个角色</h2>
     * <p/>
     * DELETE /api/msc-roles/{name}
     *
     * @return 被删除的角色
     */
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public Role destroy() {

        if (role == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The role no must be specified");
        }

        try {
            service.destroy(role);
        } catch (MscRoleException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return role;
    }

    @BeforeFilter({"update", "destroy"})
    public void initRole(@PathVariable("name") String name) {

        this.role = service.findByName(name);//find it by name
    }
}
