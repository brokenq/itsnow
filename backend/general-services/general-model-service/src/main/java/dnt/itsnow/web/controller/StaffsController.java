package dnt.itsnow.web.controller;

import dnt.itsnow.exception.StaffException;
import dnt.itsnow.model.Role;
import dnt.itsnow.model.Staff;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.web.annotation.BeforeFilter;
import net.happyonroad.platform.web.exception.WebClientSideException;
import net.happyonroad.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * <h1>员工管理控制器</h1>
 * <pre>
 * <b>HTTP     URI                   方法      含义  </b>
 * # GET      /api/staffs?keyword={}  index     列出所有员工，支持过滤，分页，排序等
 * # GET      /api/staffs             show      列出特定的员工
 * # POST     /api/staffs             create    创建员工，账户信息通过HTTP BODY提交
 * # PUT      /api/staffs/{sn}        update    修改员工，账户信息通过HTTP BODY提交
 * # DELETE   /api/staffs/{sn}        destroy   删除员工
 * </pre>
 */
@RestController
@RequestMapping("/api/staffs")
public class StaffsController extends SessionSupportController<Staff> {

    @Autowired
    private StaffService staffService;

    private Staff staff;

    /**
     * <h2>获得所有的员工</h2>
     *
     * GET /api/staffs
     * @param keyword 查询关键字
     * @return 员工列表
     */
    @RequestMapping
    public Page<Staff> index(@RequestParam(value = "keyword", required = false) String keyword){

        logger.debug("Listing staffs by keyword: {}", keyword);

        indexPage = staffService.findAll(keyword, pageRequest);

        logger.debug("Listed  {}", indexPage.getContent());

        return indexPage;
    }

    /**
     * <h2>查看一个员工</h2>
     *
     * GET /api/staffs/{no}
     *
     * @return 员工
     */
    @RequestMapping("{no}")
    public Staff show(){
        return staff;
    }

    /**
     * <h2>创建一个员工</h2>
     *
     * POST /api/staffs
     * @param staff 待新建的员工
     * @return 新建的员工
     */
    @RequestMapping(method = RequestMethod.POST)
    public Staff create(@Valid @RequestBody Staff staff){

        logger.info("Creating {}", staff);

        try {
            staff = staffService.create(staff);
        } catch (StaffException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }  catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created  {}", staff);

        return staff;
    }

    /**
     * <h2>更新一个员工</h2>
     *
     * PUT /api/staffs/{no}
     * @param staff 待修改的员工
     * @return 被更新的员工
     */
    @RequestMapping(value = "{no}", method = RequestMethod.PUT)
    public Staff update(@Valid @RequestBody Staff staff){

        logger.info("Updating {}", staff);

        this.staff.apply(staff);
        try {
            staffService.update(this.staff);
        } catch (StaffException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }  catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated  {}", this.staff);

        return this.staff;
    }

    /**
     * <h2>删除一个员工</h2>
     *
     * DELETE /api/staffs/{no}
     *
     * @return 被删除的员工
     */
    @RequestMapping(value = "{no}", method = RequestMethod.DELETE)
    public Staff destroy(){

        logger.warn("Deleting {}", staff);

        try {
            staffService.destroy(staff);
        } catch (StaffException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }  catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted  {}", staff);

        return staff;
    }

    @RequestMapping(value = "check/{no}", method = RequestMethod.GET)
    public HashMap checkUnique(@PathVariable("no") String no){
        Staff staff = staffService.findByNo(no);
        if( staff != null ){
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate staff NO.: " + staff.getNo());
        }else{
            return new HashMap();
        }
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initStaff(@PathVariable("no") String no){
        this.staff = staffService.findByNo(no);//find it by no
    }

}
