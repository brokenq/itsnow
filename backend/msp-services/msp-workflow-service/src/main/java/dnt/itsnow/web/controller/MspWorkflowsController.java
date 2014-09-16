package dnt.itsnow.web.controller;

import dnt.itsnow.exception.WorkflowException;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MspWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Workflows Controller</h1>
 */
@RestController
@RequestMapping("/api/mspWorkflows")
public class MspWorkflowsController extends SessionSupportController<Workflow> {

    @Autowired
    private MspWorkflowService mspWorkflowService;

    private Workflow workflow;

    /**
     * <h2>获得所有的地点</h2>
     *
     * GET /api/workflows
     *
     * @return 地点列表
     */
    @RequestMapping
    public List<Workflow> index(@RequestParam(value = "keyword", required = false) String keyword){
        logger.debug("Listing Workflow");

        indexPage = mspWorkflowService.findAll(keyword, pageRequest);

        logger.debug("Listed Workflow number {}", indexPage.getNumber());
        return indexPage.getContent();
    }

    /**
     * <h2>查看一个地点</h2>
     *
     * GET /api/workflows/{sn}
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
            workflow = mspWorkflowService.create(workflow);
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", workflow.getName());
        return workflow;
    }

    /**
     * <h2>更新一个地点</h2>
     *
     * PUT /api/workflows/{sn}
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
            mspWorkflowService.update(workflow);
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", workflow.getName());

        return this.workflow;
    }

    /**
     * <h2>删除一个地点</h2>
     *
     * DELETE /api/workflows/{sn}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public Workflow destroy(){

        if (workflow == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The workflow no must be specified");
        }

        try {
            mspWorkflowService.destroy(workflow);
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return workflow;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initWorkflow(@PathVariable("sn") String sn){

        this.workflow = mspWorkflowService.findBySn(sn);//find it by sn
    }
}
