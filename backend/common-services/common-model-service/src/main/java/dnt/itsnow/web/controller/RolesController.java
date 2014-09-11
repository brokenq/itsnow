/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Role;
import dnt.itsnow.model.Role;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Roles Controller</h1>
 */
@RestController
@RequestMapping("/api/roles")
public class RolesController extends SessionSupportController<Role> {
    Role role;

    /**
     * <h2>获得所有的角色</h2>
     *
     * GET /api/roles
     *
     * @return 角色
     */
    @RequestMapping
    public List<Role> index(){
        return null;
    }

    /**
     * <h2>查看一个角色</h2>
     *
     * GET /api/roles/{no}
     *
     * @return 服务目录
     */
    @RequestMapping("{no}")
    public Role show(){
        return role;
    }

    /**
     * <h2>创建一个角色</h2>
     *
     * POST /api/roles
     *
     * @return 新建的角色
     */
    @RequestMapping(method = RequestMethod.POST)
    public Role create(@Valid @RequestBody Role role){
        return role;
    }

    /**
     * <h2>更新一个角色</h2>
     *
     * PUT /api/roles/{sn}
     *
     * @return 被更新的角色
     */
    @RequestMapping(value = "{no}", method = RequestMethod.PUT)
    public Role update(@Valid @RequestBody Role role){
        this.role.apply(role);
        //TODO SAVE IT
        return this.role;
    }

    /**
     * <h2>删除一个角色</h2>
     *
     * DELETE /api/roles/{sn}
     *
     * @return 被删除的角色
     */
    @RequestMapping(value = "{no}", method = RequestMethod.DELETE)
    public Role destroy(){
        return null;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initRole(@PathVariable("no") String no){
        role = null;//find it by sn
    }
}
