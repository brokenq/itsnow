/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Staff;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>WorkTime Controller</h1>
 */
@RestController
@RequestMapping("/api/workTimes")
public class StaffsController extends SessionSupportController<Staff> {
    WorkTime workTime;

    /**
     * <h2>获得所有的工作时间</h2>
     *
     * GET /api/workTimes
     *
     * @return 工作时间
     */
    @RequestMapping
    public List<WorkTime> index(){
        return null;
    }

    /**
     * <h2>查看一个工作时间</h2>
     *
     * GET /api/workTimes/{no}
     *
     * @return 服务目录
     */
    @RequestMapping("{no}")
    public WorkTime show(){
        return workTime;
    }

    /**
     * <h2>创建一个工作时间</h2>
     *
     * POST /api/workTimes
     *
     * @return 新建的工作时间
     */
    @RequestMapping(method = RequestMethod.POST)
    public WorkTime create(@Valid @RequestBody WorkTime workTime){
        return workTime;
    }

    /**
     * <h2>更新一个工作时间</h2>
     *
     * PUT /api/workTimes/{sn}
     *
     * @return 被更新的工作时间
     */
    @RequestMapping(value = "{no}", method = RequestMethod.PUT)
    public WorkTime update(@Valid @RequestBody WorkTime workTime){
        this.workTime.apply(workTime);
        //TODO SAVE IT
        return this.workTime;
    }

    /**
     * <h2>删除一个工作时间</h2>
     *
     * DELETE /api/workTimes/{sn}
     *
     * @return 被删除的工作时间
     */
    @RequestMapping(value = "{no}", method = RequestMethod.DELETE)
    public WorkTime destroy(){
        return null;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initWorkTime(@PathVariable("no") String no){
        workTime = null;//find it by sn
    }
}
