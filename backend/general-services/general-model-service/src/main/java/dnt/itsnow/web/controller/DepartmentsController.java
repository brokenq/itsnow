/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.Exception.DepartmentException;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Departments Controller</h1>
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentsController extends SessionSupportController<Department> {

    @Autowired
    private DepartmentService departmentService;

    private Department department;

    /**
     * <h2>获得所有的地点</h2>
     *
     * GET /api/departments
     *
     * @return 地点列表
     */
    @RequestMapping
    public List<Department> index(@RequestParam(value = "keyword", required = false) String keyword){
        logger.debug("Listing Department");

        indexPage = departmentService.findAll(keyword, pageRequest);

        logger.debug("Listed Department number {}", indexPage.getNumber());
        return indexPage.getContent();
    }

    /**
     * <h2>查看一个地点</h2>
     *
     * GET /api/departments/{sn}
     *
     * @return 地点
     */
    @RequestMapping("/{sn}")
    public Department show(){
        if (department == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The department no must be specified");
        }
        return department;
    }

    /**
     * <h2>创建一个地点</h2>
     *
     * POST /api/departments
     *
     * @return 新建的地点
     */
    @RequestMapping(method = RequestMethod.POST)
    public Department create(@Valid @RequestBody Department department){
        logger.info("Creating {}", department.getName());

        try {
            department = departmentService.create(department);
        } catch (DepartmentException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", department.getName());
        return department;
    }

    /**
     * <h2>更新一个地点</h2>
     *
     * PUT /api/departments/{sn}
     *
     * @return 被更新的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public Department update(@Valid @RequestBody Department department){

        logger.info("Updateing {}", department.getName());

        if (department == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The department no must be specified");
        }

        this.department.apply(department);
        try {
            departmentService.update(department);
        } catch (DepartmentException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", department.getName());

        return this.department;
    }

    /**
     * <h2>删除一个地点</h2>
     *
     * DELETE /api/departments/{sn}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public Department destroy(){

        if (department == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The department no must be specified");
        }

        try {
            departmentService.destroy(department);
        } catch (DepartmentException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return department;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initDepartment(@PathVariable("sn") String sn){

        this.department = departmentService.findBySn(sn);//find it by sn
    }
}
