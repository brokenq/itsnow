/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Department;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Departments Controller</h1>
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentsController extends SessionSupportController<Department> {
    Department department;

    /**
     * <h2>获得所有的部门</h2>
     *
     * GET /api/departments
     *
     * @return 部门
     */
    @RequestMapping
    public List<Department> index(){
        return null;
    }

    /**
     * <h2>查看一个部门</h2>
     *
     * GET /api/departments/{no}
     *
     * @return 部门
     */
    @RequestMapping("{no}")
    public Department show(){
        return department;
    }

    /**
     * <h2>创建一个部门</h2>
     *
     * POST /api/departments
     *
     * @return 新建的部门
     */
    @RequestMapping(method = RequestMethod.POST)
    public Department create(@Valid @RequestBody Department department){
        return department;
    }

    /**
     * <h2>更新一个部门</h2>
     *
     * PUT /api/departments/{sn}
     *
     * @return 被更新的部门
     */
    @RequestMapping(value = "{no}", method = RequestMethod.PUT)
    public Department update(@Valid @RequestBody Department department){
        this.department.apply(department);
        //TODO SAVE IT
        return this.department;
    }

    /**
     * <h2>删除一个部门</h2>
     *
     * DELETE /api/departments/{sn}
     *
     * @return 被删除的部门
     */
    @RequestMapping(value = "{no}", method = RequestMethod.DELETE)
    public Department destroy(){
        return null;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initDepartment(@PathVariable("no") String no){
        department = null;//find it by sn
    }
}
