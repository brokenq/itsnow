/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.RoleException;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>流程字典的控制器</h1>
 * <pre>
 * <b>HTTP    URI                 方法      含义  </b>
 *  GET      /api/roles           index     列出所有的流程字典，并且分页显示
 *  GET      /api/roles/{sn}      show      列出特定的流程字典记录
 *  POST     /api/roles/          create    创建一个流程字典
 *  PUT      /api/roles/{sn}      update    修改一个指定的流程字典
 *  DELETE   /api/roles/{sn}      delete    删除指定的流程字典记录
 * </pre>
 */
@RestController
@RequestMapping("/api/roles")
public class RolesController extends SessionSupportController<Role> {

    @Autowired
    private RoleService service;

    private Role role;

    /**
     * <h2>获得所有的流程字典</h2>
     *
     * GET /api/roles
     *
     * @return 字典列表
     */
    @RequestMapping
    public List<Role> index(@RequestParam(value = "keyword", required = false) String keyword){
        logger.debug("Listing Role");

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed Role number {}", indexPage.getNumber());
        return indexPage.getContent();
    }

    /**
     * <h2>查看一个字典</h2>
     *
     * GET /api/roles/{sn}
     *
     * @return 地点
     */
    @RequestMapping("/{sn}")
    public Role show(){
        if (role == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The role no must be specified");
        }
        return role;
    }

    /**
     * <h2>创建一个地点</h2>
     *
     * POST /api/roles
     *
     * @return 新建的地点
     */
    @RequestMapping(method = RequestMethod.POST)
    public Role create(@Valid @RequestBody Role dictionary){
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
     * <h2>更新一个地点</h2>
     *
     * PUT /api/roles/{sn}
     *
     * @return 被更新的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public Role update(@Valid @RequestBody Role dictionary){

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
     * <h2>删除一个地点</h2>
     *
     * DELETE /api/roles/{sn}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public Role destroy(){

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
    public void initRole(@PathVariable("sn") String sn){

        this.role = service.findBySn(sn);//find it by sn
    }
}
