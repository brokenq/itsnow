package dnt.itsnow.web.controller;

import dnt.itsnow.exception.StaffException;
import dnt.itsnow.model.Staff;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <h1>Staffs Controller</h1>
 */
@RestController
@RequestMapping("/api/staffs")
public class StaffsController extends SessionSupportController<Staff> {

    @Autowired
    private StaffService staffService;

    private Staff staff;

    /**
     * <h2>获得所有的地点</h2>
     *
     * GET /api/staffs
     *
     * @return 地点列表
     */
    @RequestMapping
    public Page<Staff> index(@RequestParam(value = "keyword", required = false) String keyword){
        logger.debug("Listing Staffs by keyword: {}", keyword);

        indexPage = staffService.findAll(keyword, pageRequest);

        logger.debug("Listed  {}", indexPage);
        return indexPage;
    }

    /**
     * <h2>查看一个地点</h2>
     *
     * GET /api/staffs/{no}
     *
     * @return 地点
     */
    @RequestMapping("/{no}")
    public Staff show(){
        if (staff == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The staff no must be specified");
        }
        return staff;
    }

    /**
     * <h2>创建一个地点</h2>
     *
     * POST /api/staffs
     *
     * @return 新建的地点
     */
    @RequestMapping(method = RequestMethod.POST)
    public Staff create(@Valid @RequestBody Staff staff){
        logger.info("Creating {}", staff.getName());

        try {
            staff = staffService.create(staff);
        } catch (StaffException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", staff.getName());
        return staff;
    }

    /**
     * <h2>更新一个地点</h2>
     *
     * PUT /api/staffs/{no}
     *
     * @return 被更新的地点
     */
    @RequestMapping(value = "/{no}", method = RequestMethod.PUT)
    public Staff update(@Valid @RequestBody Staff staff){

        logger.info("Updateing {}", staff.getName());

        if (staff == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The staff no must be specified");
        }

        this.staff.apply(staff);
        try {
            staffService.update(staff);
        } catch (StaffException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", staff.getName());

        return this.staff;
    }

    /**
     * <h2>删除一个地点</h2>
     *
     * DELETE /api/staffs/{no}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "/{no}", method = RequestMethod.DELETE)
    public Staff destroy(){

        if (staff == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The staff no must be specified");
        }

        try {
            staffService.destroy(staff);
        } catch (StaffException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return staff;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initStaff(@PathVariable("no") String no){

        this.staff = staffService.findByNo(no);//find it by no
    }
}
