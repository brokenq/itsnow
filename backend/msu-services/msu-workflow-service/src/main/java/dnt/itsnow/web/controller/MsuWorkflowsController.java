package dnt.itsnow.web.controller;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.exception.WorkflowException;
import dnt.itsnow.model.ActReProcdef;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.WorkflowService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>MSU工作流配置信息控制器</h1>
 * <pre>
 * <b>HTTP    URI                          方法      含义</b>
 * # GET      /api/msu-workflows           index     列出所有工作流配置信息支持分页、关键字过滤
 * # GET      /api/msu-workflows/{sn}      show      列出指定的工作流配置信息
 * # POST     /api/msu-workflows           create    创建工作流配置信息，账户信息通过HTTP BODY提交
 * # POST     /api/msu-workflows/upload    upload    上传工作流配置文件
 * # PUT      /api/msu-workflows/{sn}      update    修改工作流配置信息，账户信息通过HTTP BODY提交
 * # DELETE   /api/msu-workflows/{sn}      destroy   删除工作流配置信息
 * </pre>
 */
@RestController
@RequestMapping("/api/msu-workflows")
public class MsuWorkflowsController extends SessionSupportController<Workflow> {

    @Autowired
    private WorkflowService service;

    private Workflow workflow;

    private MultipartFile file;

    @Autowired
    private ActivitiEngineService activitiEngineService;

    /**
     * <h2>获得所有的工作流配置信息列表</h2>
     * <p/>
     * GET /api/msu-workflows
     *
     * @param keyword 关键字
     * @return 工作流配置信息列表
     */
    @RequestMapping
    public Page<Workflow> index(@RequestParam(value = "keyword", required = false) String keyword) {

        logger.debug("Listing Msu-workflow by keyword: {}", keyword);

        indexPage = service.findAll(keyword, pageRequest, Workflow.PUBLIC_SERVICE_ITEM);

        logger.debug("Listed  {}", indexPage);

        return indexPage;
    }

    /**
     * <h2>查看一个工作流配置</h2>
     * <p/>
     * GET /api/msu-workflows/{sn}
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
     * POST /api/msu-workflows
     *
     * @param workflow 工作流配置信息
     * @return 新建的工作流配置信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public Workflow create(@Valid @RequestBody Workflow workflow) {

        logger.info("Creating {}", workflow);

        try {
            // 部署单个流程定义
            InputStream inputStream = file.getInputStream();
            Deployment deployment = activitiEngineService.deploySingleProcess(inputStream, workflow.getName(), workflow.getDictionary().getVal());
            if (deployment == null) {
                throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, "Msu-workflow create failed");
            }
            ProcessDefinition processDefinition = activitiEngineService.getProcessDefinitionByDeploymentId(deployment.getId());
            ActReProcdef actReProcdef = new ActReProcdef();
            actReProcdef.setId(processDefinition.getId());
            workflow.setActReProcdef(actReProcdef);
        } catch (IOException e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, "Msu-workflow create failed, " + e.getMessage());
        }

        try {
            workflow.setServiceItemType(Workflow.PUBLIC_SERVICE_ITEM);
            workflow = service.create(workflow);
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created  {}", workflow);

        return workflow;
    }

    /**
     * <h2>上传流程配置文件</h2>
     * <p></p>
     * POST /api/msu-workflows/upload
     *
     * @param request 请求
     * @param file    上传的文件
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {

        this.file = file;
        logger.info("Uploading {}", this.file);
        logger.info("Get class name {}", Workflow.class);

        try {
            // 判断文件是否为空
            if (!this.file.isEmpty()) {
                // 文件保存路径
                String filePath = request.getSession().getServletContext().getRealPath("/") + "/data/"
                        + this.file.getOriginalFilename();
                File workflowFile = new File(filePath);
                // 若路径不存在都自动创建
                if (!workflowFile.exists()) {
                    workflowFile.mkdirs();
                }
                // 转存文件
                this.file.transferTo(workflowFile);
            }
        } catch (IOException e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, "Upload file failed cause by network, " + e.getMessage());
        } catch (IllegalStateException e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Uploaded  {}", this.file);
    }

    /**
     * <h2>更新一个工作流配置信息</h2>
     * <p/>
     * PUT /api/msu-workflows/{sn}
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
     * DELETE /api/msu-workflows/{sn}
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public void destroy() {

        logger.warn("Deleting {}", workflow);

        try {
            //deleteProcessDeploy
            activitiEngineService.deleteProcessDeploy(workflow.getActReDeployment().getId());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, "Destroy Msu-workflow failed, "+e.getMessage());
        }

        try {
            service.destroy(workflow);
        } catch (WorkflowException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted  {}", workflow);
    }

    @RequestMapping(value = "{name}/check", method = RequestMethod.GET)
    public HashMap checkUnique(@PathVariable("name") String name) {

        logger.debug("Check   unique Msu-workflow name: {}", name);

        Workflow workflow = service.findByName(name);

        logger.debug("Checked unique {}", workflow);

        if (workflow != null) {
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate Msu-workflow name: " + workflow.getName());
        } else {
            return new HashMap();
        }
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initWorkflow(@PathVariable("sn") String sn) {
        this.workflow = service.findBySn(sn, Workflow.PUBLIC_SERVICE_ITEM);//find it by sn
    }

}
