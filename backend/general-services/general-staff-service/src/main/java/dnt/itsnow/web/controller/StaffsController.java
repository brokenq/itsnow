/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Staff;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Staffs Controller</h1>
 */
@RestController
@RequestMapping("/api/staffs")
public class StaffsController extends SessionSupportController<Staff>{
    Staff staff;

    /**
     * <h2>获得所有的员工</h2>
     *
     * GET /api/staffs
     *
     * @return 员工
     */
    @RequestMapping
    public List<Staff> index(){
        return null;
    }

    /**
     * <h2>查看一个员工</h2>
     *
     * GET /api/staffs/{no}
     *
     * @return 服务目录
     */
    @RequestMapping("{no}")
    public Staff show(){
        return staff;
    }

    /**
     * <h2>创建一个员工</h2>
     *
     * POST /api/staffs
     *
     * @return 新建的员工
     */
    @RequestMapping(method = RequestMethod.POST)
    public Staff create(@Valid @RequestBody Staff staff){
        return staff;
    }

    /**
     * <h2>更新一个员工</h2>
     *
     * PUT /api/staffs/{sn}
     *
     * @return 被更新的员工
     */
    @RequestMapping(value = "{no}", method = RequestMethod.PUT)
    public Staff update(@Valid @RequestBody Staff staff){
        this.staff.apply(staff);
        //TODO SAVE IT
        return this.staff;
    }

    /**
     * <h2>删除一个员工</h2>
     *
     * DELETE /api/staffs/{sn}
     *
     * @return 被删除的员工
     */
    @RequestMapping(value = "{no}", method = RequestMethod.DELETE)
    public Staff destroy(){
        return null;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initStaff(@PathVariable("no") String no){
        staff = null;//find it by sn
    }
}
