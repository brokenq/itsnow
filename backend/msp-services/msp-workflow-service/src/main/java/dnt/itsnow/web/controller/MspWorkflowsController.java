package dnt.itsnow.web.controller;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.exception.WorkflowException;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.InputStream;

/**
 * <h1>MSP工作流配置信息控制器</h1>
 * <pre>
 * <b>HTTP    URI                          方法      含义</b>
 * # GET      /api/msp_workflows           index     列出所有工作流配置信息支持分页、关键字过滤
 * # GET      /api/msp_workflows/{sn}      show      列出指定的工作流配置信息
 * # POST     /api/msp_workflows           create    创建工作流配置信息，账户信息通过HTTP BODY提交
 * # PUT      /api/msp_workflows/{sn}      update    修改工作流配置信息，账户信息通过HTTP BODY提交
 * # DELETE   /api/msp_workflows/{sn}      destroy   删除工作流配置信息
 * </pre>
 */
@RestController
@RequestMapping("/api/msp_workflows")
public class MspWorkflowsController extends SessionSupportController<Workflow> {

    @Autowired
    private WorkflowService service;

    @Autowired
    private ActivitiEngineService activitiEngineService;

    private Workflow workflow;

    /**
     * <h2>获得所有的工作流配置信息列表</h2>
     * <p/>
     * GET /api/workflows
     *
     * @param keyword 关键字
     * @return 工作流配置信息列表
     */
    @RequestMapping
    public Page<Workflow> index(@RequestParam(value = "keyword", required = false) String keyword) {

        logger.debug("Listing Workflows by keyword: {}", keyword);

        indexPage = service.findAll(keyword, pageRequest, Workflow.PRIVATE_SERVICE_ITEM);

        logger.debug("Listed  {}", indexPage);

        return indexPage;
    }

    /**
     * <h2>查看一个工作流配置</h2>
     * <p/>
     * GET /api/workflows/{sn}
     *
     * @return 工作流配置信息
     */
    @RequestMapping("{sn}")
    public Workflow show() {
        return workflow;
    }

    /**
     * <h2>创建一个工作流配置信息</h2>
     * <p/>
     * POST /api/workflows
     *
     * @param workflow 工作流配置信息
     * @return 新建的工作流配置信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public Workflow create(@Valid @RequestBody Workflow workflow, @RequestParam("file") MultipartFile file) {

        logger.info("Creating {}", workflow);

        try {
            workflow = service.create(workflow);

            // 部署单个流程定义
            InputStream inputStream = file.getInputStream() ;
            activitiEngineService.deploySingleProcess(inputStream, workflow.getName(), workflow.getDictionary().getVal());
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created  {}", workflow);

        return workflow;
    }

    /**
     * <h2>更新一个工作流配置信息</h2>
     * <p/>
     * PUT /api/workflows/{sn}
     *
     * @param workflow 工作流配置信息
     * @return 被更新的工作流配置信息
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.PUT)
    public Workflow update(@Valid @RequestBody Workflow workflow) {

        logger.info("Updating {}", workflow);

        this.workflow.apply(workflow);
        try {
            service.update(this.workflow);
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated  {}", this.workflow);

        return this.workflow;
    }

    /**
     * <h2>删除一个工作流配置信息</h2>
     * <p/>
     * DELETE /api/workflows/{sn}
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public void destroy() {

        logger.warn("Deleting {}", workflow);

        try {
            service.destroy(workflow);
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted  {}", workflow);

    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initWorkflow(@PathVariable("sn") String sn) {
        this.workflow = service.findBySn(sn, Workflow.PRIVATE_SERVICE_ITEM);//find it by sn
    }

}
