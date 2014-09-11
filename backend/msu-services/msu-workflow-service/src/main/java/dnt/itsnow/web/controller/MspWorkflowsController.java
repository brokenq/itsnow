/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.MspWorkflow;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>MspWorkflows Controller</h1>
 */
@RestController
@RequestMapping("/api/mspWorkflows")
public class MspWorkflowsController extends SessionSupportController<MspWorkflow> {
    MspWorkflow mspWorkflow;

    /**
     * <h2>获得所有的工作流</h2>
     *
     * GET /api/mspWorkflows
     *
     * @return 工作流
     */
    @RequestMapping
    public List<MspWorkflow> index(){
        return null;
    }

    /**
     * <h2>查看一个工作流</h2>
     *
     * GET /api/mspWorkflows/{no}
     *
     * @return 服务目录
     */
    @RequestMapping("{no}")
    public MspWorkflow show(){
        return mspWorkflow;
    }

    /**
     * <h2>创建一个工作流</h2>
     *
     * POST /api/mspWorkflows
     *
     * @return 新建的工作流
     */
    @RequestMapping(method = RequestMethod.POST)
    public MspWorkflow create(@Valid @RequestBody MspWorkflow mspWorkflow){
        return mspWorkflow;
    }

    /**
     * <h2>更新一个工作流</h2>
     *
     * PUT /api/mspWorkflows/{sn}
     *
     * @return 被更新的工作流
     */
    @RequestMapping(value = "{no}", method = RequestMethod.PUT)
    public MspWorkflow update(@Valid @RequestBody MspWorkflow mspWorkflow){
        this.mspWorkflow.apply(mspWorkflow);
        //TODO SAVE IT
        return this.mspWorkflow;
    }

    /**
     * <h2>删除一个工作流</h2>
     *
     * DELETE /api/mspWorkflows/{sn}
     *
     * @return 被删除的工作流
     */
    @RequestMapping(value = "{no}", method = RequestMethod.DELETE)
    public MspWorkflow destroy(){
        return null;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initMspWorkflow(@PathVariable("no") String no){
        mspWorkflow = null;//find it by sn
    }
}
