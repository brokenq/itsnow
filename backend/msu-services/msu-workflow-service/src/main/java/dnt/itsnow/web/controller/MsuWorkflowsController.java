package dnt.itsnow.web.controller;

import dnt.itsnow.exception.WorkflowException;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Workflows Controller</h1>
 */
@RestController
@RequestMapping("/api/msu-workflows")
public class MsuWorkflowsController extends SessionSupportController<Workflow> {

    @Autowired
    private WorkflowService service;

    private Workflow workflow;

    /**
     * <h2>获得所有的地点</h2>
     *
     * GET /api/workflows
     *
     * @return 地点列表
     */
    @RequestMapping
    public Page<Workflow> index(@RequestParam(value = "keyword", required = false) String keyword){
        logger.debug("Listing Workflow by keyword: {}", keyword);

        indexPage = service.findAll(keyword, pageRequest, "0");

        logger.debug("Found  {}", indexPage);
        return indexPage;
    }

    /**
     * <h2>查看一个地点</h2>
     *
     * GET /api/msu-workflows/{sn}
     *
     * @return 地点
     */
    @RequestMapping("/{sn}")
    public Workflow show(){
        if (workflow == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The workflow no must be specified");
        }
        return workflow;
    }

    /**
     * <h2>创建一个地点</h2>
     *
     * POST /api/workflows
     *
     * @return 新建的地点
     */
    @RequestMapping(method = RequestMethod.POST)
    public Workflow create(@Valid @RequestBody Workflow workflow){
        logger.info("Creating {}", workflow.getName());

        try {
            workflow = service.create(workflow);
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", workflow.getName());
        return workflow;
    }

    /**
     * <h2>更新一个地点</h2>
     *
     * PUT /api/msu-workflows/{sn}
     *
     * @return 被更新的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public Workflow update(@Valid @RequestBody Workflow workflow){

        logger.info("Updateing {}", workflow.getName());

        if (workflow == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The workflow no must be specified");
        }

        this.workflow.apply(workflow);
        try {
            service.update(workflow);
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", workflow.getName());

        return this.workflow;
    }

    /**
     * <h2>删除一个地点</h2>
     *
     * DELETE /api/msu-workflows/{sn}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public Workflow destroy(){

        if (workflow == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The workflow no must be specified");
        }

        try {
            service.destroy(workflow);
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return workflow;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initWorkflow(@PathVariable("sn") String sn){

        this.workflow = service.findBySn(sn, "0");//find it by sn
    }
}
