/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.DepartmentException;
import dnt.itsnow.model.Department;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>部门控制器</h1>
 * <pre>
 * <b>HTTP    URI                                    方法       含义</b>
 * # GET      /api/departments?isTree={}             index      列出所有部门，支持树形结构展现
 * # GET      /api/departments/{sn}                  show       列出特定的部门信息
 * # GET      /api/departments/check_child/{id}      checkChild 检查是否含有子部门
 * # POST     /api/departments                       create     创建部门，账户信息通过HTTP BODY提交
 * # PUT      /api/departments/{sn}                  update     修改部门，账户信息通过HTTP BODY提交
 * # DELETE   /api/departments/{sn}                  destroy    删除部门
 * </pre>
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentsController extends SessionSupportController<Department> {

    @Autowired
    private DepartmentService departmentService;

    private Department department;

    /**
     * <h2>获得所有的部门</h2>
     * <p/>
     * GET /api/departments
     *
     * @param keyword 查询关键字
     * @param isTree  树形结构标记
     * @return 部门列表
     */
    @RequestMapping
    public List<Department> index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "isTree", defaultValue = "true") boolean isTree) {

        logger.debug("Listing Department by keyword {}, show tree is {}", keyword, isTree);

        List<Department> departments = departmentService.findAll(keyword, isTree);

        logger.debug("Listed  {}", departments);

        return departments;
    }

    /**
     * <h2>查看一个部门</h2>
     * <p/>
     * GET /api/departments/{sn}
     *
     * @return 部门
     */
    @RequestMapping("{sn}")
    public Department show() {
        return department;
    }

    @RequestMapping("/check_child/{id}")
    public Boolean checkChild(@PathVariable("id") long id) {
        List<Department> departments = departmentService.findAllByParentId(id);
        if (departments != null && departments.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <h2>创建一个部门</h2>
     * <p/>
     * POST /api/departments
     *
     * @param department 待创建的部门
     * @return 新建的部门
     */
    @RequestMapping(method = RequestMethod.POST)
    public Department create(@Valid @RequestBody Department department) {

        logger.info("Creating {}", department);

        try {
            department = departmentService.create(department);
        } catch (DepartmentException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created  {}", department);

        return department;
    }

    /**
     * <h2>更新一个部门</h2>
     * <p/>
     * PUT /api/departments/{sn}
     *
     * @param department 待更新的部门
     * @return 被更新的部门
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.PUT)
    public Department update(@Valid @RequestBody Department department) {

        logger.info("Updating {}", department);

        this.department.apply(department);
        try {
            departmentService.update(this.department);
        } catch (DepartmentException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated  {}", this.department);

        return this.department;
    }

    /**
     * <h2>删除一个部门</h2>
     * <p/>
     * DELETE /api/departments/{sn}
     *
     * @return 被删除的部门
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public void destroy() {

        logger.warn("Deleting {}", department);

        try {
            departmentService.destroy(department);
        } catch (DepartmentException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted  {}", department);
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initDepartment(@PathVariable("sn") String sn) {
        this.department = departmentService.findBySn(sn);//find it by sn
    }

}
