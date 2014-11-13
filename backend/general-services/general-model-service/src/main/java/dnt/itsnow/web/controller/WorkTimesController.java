package dnt.itsnow.web.controller;

import dnt.itsnow.exception.WorkTimeException;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <h1>工作时间管理控制器</h1>
 * <pre>
 * <b>HTTP     URI                        方法      含义  </b>
 * # GET      /api/work_times?keyword={}  index     列出所有工作时间，支持过滤，分页，排序等
 * # GET      /api/work_times             show      列出特定的工作时间
 * # POST     /api/work_times             create    创建工作时间，账户信息通过HTTP BODY提交
 * # PUT      /api/work_times/{sn}        update    修改工作时间，账户信息通过HTTP BODY提交
 * # DELETE   /api/work_times/{sn}        destroy   删除工作时间
 * </pre>
 */
@RestController
@RequestMapping("/api/work_times")
public class WorkTimesController extends SessionSupportController<WorkTime> {

    WorkTime workTime;

    @Autowired
    private WorkTimeService service;

    /**
     * <h2>获得所有的员工</h2>
     *
     * GET /api/work_times
     * @param keyword 查询关键字
     * @return 员工列表
     */
    @RequestMapping
    public Page<WorkTime> index(@RequestParam(value = "keyword", required = false) String keyword){

        logger.debug("Listing work times by keyword: {}", keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed  {}", indexPage);

        return indexPage;
    }

    /**
     * <h2>查看一个工作时间</h2>
     * <p/>
     * GET /api/work_times/{sn}
     *
     * @return 工作时间
     */
    @RequestMapping("{sn}")
    public WorkTime show(){
        return workTime;
    }

    /**
     * <h2>创建一个工作时间</h2>
     * <p/>
     * POST /api/work_times
     * @param workTime 待新建的工作时间
     * @return 新建的工作时间
     */
    @RequestMapping(method = RequestMethod.POST)
    public WorkTime create(@Valid @RequestBody WorkTime workTime){

        logger.info("Creating {}", workTime);

        try {
            workTime = service.create(workTime);
        } catch (WorkTimeException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }  catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created  {}", workTime);

        return workTime;
    }

    /**
     * <h2>更新一个工作时间</h2>
     * <p/>
     * PUT /api/work_times/{sn}
     * @param workTime 待更新的工作时间
     * @return 被更新的工作时间
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.PUT)
    public WorkTime update(@Valid @RequestBody WorkTime workTime){

        logger.info("Updating {}", workTime);

        this.workTime.apply(workTime);
        try {
            service.update(this.workTime);
        } catch (WorkTimeException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }  catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated  {}", this.workTime);

        return this.workTime;
    }

    /**
     * <h2>删除一个工作时间</h2>
     * <p/>
     * DELETE /api/work_times/{sn}
     *
     * @return 被删除的工作时间
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public WorkTime destroy(){

        logger.warn("Deleting {}", workTime);

        try {
            service.destroy(workTime.getSn());
        } catch (WorkTimeException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }  catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted  {}", workTime);

        return workTime;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initWorkTime(@PathVariable("sn") String sn){
        this.workTime = service.findBySn(sn);//find it by sn
    }
    
}
