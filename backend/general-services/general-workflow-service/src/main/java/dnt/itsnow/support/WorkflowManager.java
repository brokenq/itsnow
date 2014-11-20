package dnt.itsnow.support;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.exception.WorkflowException;
import dnt.itsnow.model.ActReProcdef;
import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.repository.WorkflowRepository;
import dnt.itsnow.service.CommonServiceItemService;
import dnt.itsnow.service.PrivateServiceItemService;
import dnt.itsnow.service.PublicServiceItemService;
import dnt.itsnow.service.WorkflowService;
import dnt.spring.Bean;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>工作流管理业务实现层</h1>
 */
@Service
public class WorkflowManager extends Bean implements WorkflowService {

    @Autowired
    private WorkflowRepository repository;

    @Autowired
    private ActivitiEngineService activitiEngineService;

    @Autowired
    private CommonServiceItemService commonServiceItemService;

    @Autowired
    private PrivateServiceItemService privateServiceItemService;

    @Override
    public Workflow create(Workflow workflow, InputStream inputStream) throws WorkflowException {

        logger.info("Creating {}", workflow);

        if (workflow == null) {
            throw new WorkflowException("Workflow entry can not be empty.");
        }

        try {
            // 部署单个流程定义
            Deployment deployment = activitiEngineService.deploySingleProcess(inputStream, workflow.getName(), workflow.getDictionary().getVal());
            if (deployment == null) {
                throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, "Workflow create failed");
            }
            ProcessDefinition processDefinition = activitiEngineService.getProcessDefinitionByDeploymentId(deployment.getId());
            ActReProcdef actReProcdef = new ActReProcdef();
            actReProcdef.setId(processDefinition.getId());
            workflow.setActReProcdef(actReProcdef);
        } catch (IOException e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, "Workflow create failed, " + e.getMessage());
        }

        workflow.setSn(UUID.randomUUID().toString());
        workflow.creating();
        repository.create(workflow);

        logger.info("Created  {}", workflow);

        return workflow;
    }

    @Override
    public Workflow update(Workflow workflow) throws WorkflowException {

        logger.info("Updating {}", workflow);

        if (workflow == null) {
            throw new WorkflowException("Workflow entry can not be empty.");
        }

        workflow.creating();
        repository.update(workflow);

        logger.info("Updated  {}", workflow);

        return workflow;
    }

    @Override
    public void destroy(Workflow workflow) throws WorkflowException {

        logger.warn("Deleting {}", workflow);

        if (workflow == null) {
            throw new WorkflowException("Workflow entry can not be empty.");
        }

        try {
            activitiEngineService.deleteProcessDeploy(workflow.getActReDeployment().getId());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, "Destroy workflow failed, "+e.getMessage());
        }

        repository.delete(workflow.getSn());

        logger.warn("Deleted  {}", workflow);

    }

    @Override
    public Page<Workflow> findAll(String keyword, Pageable pageable) {

        logger.debug("Finding workflows by keyword: {}", keyword);

        int total = repository.count(keyword);
        List<Workflow> workflows = new ArrayList<Workflow>();
        if (total > 0) {
            workflows = repository.findAll(keyword, pageable);
            for (Workflow workflow : workflows) {
                if (Workflow.PUBLIC_SERVICE_ITEM.equals(workflow.getServiceItemType())) {
                    workflow.setServiceItem(commonServiceItemService.findBySn(workflow.getServiceItemSn()));
                } else if (Workflow.PRIVATE_SERVICE_ITEM.equals(workflow.getServiceItemType())) {
                    workflow.setServiceItem(privateServiceItemService.findPrivateBySn(workflow.getServiceItemSn()));
                }
            }
        }

        DefaultPage<Workflow> page = new DefaultPage<Workflow>(workflows, pageable, total);

        logger.debug("Found   {}", page.getContent());

        return page;
    }

    @Override
    public Workflow findBySn(String sn) {

        logger.debug("Finding Workflow by sn: {}", sn);

        Workflow workflow = repository.findBySn(sn);

        if (null!=workflow && Workflow.PUBLIC_SERVICE_ITEM.equals(workflow.getServiceItemType())) {
            workflow.setServiceItem(commonServiceItemService.findBySn(workflow.getServiceItemSn()));
        } else if (null!=workflow && Workflow.PRIVATE_SERVICE_ITEM.equals(workflow.getServiceItemType())) {
            workflow.setServiceItem(privateServiceItemService.findPrivateBySn(workflow.getServiceItemSn()));
        }

        logger.debug("Found   {}", workflow);

        return workflow;
    }

    @Override
    public Workflow checkByName(String name) {

        logger.debug("Finding Workflow by name: {}", name);

        Workflow workflow = repository.findByName(name);

        logger.debug("Found   {}", workflow);

        return workflow;
    }

}
