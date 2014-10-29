package dnt.itsnow.support;

import dnt.itsnow.exception.WorkflowException;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.WorkflowRepository;
import dnt.itsnow.service.WorkflowService;
import dnt.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>工作流管理业务实现层</h1>
 */
@Service
public class WorkflowManager extends Bean implements WorkflowService {

    @Autowired
    private WorkflowRepository repository;

    @Override
    public Workflow create(Workflow workflow) throws WorkflowException {

        logger.info("Creating {}", workflow);

        if (workflow == null) {
            throw new WorkflowException("Workflow entry can not be empty.");
        }

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
        repository.delete(workflow.getSn());

        logger.warn("Deleted  {}", workflow);

    }

    @Override
    public Page<Workflow> findAll(String keyword, Pageable pageable, String serviceFlag) {

        logger.debug("Finding workflows by keyword: {}", keyword);

        int total = repository.count(serviceFlag, keyword);
        List<Workflow> workflows = repository.find(serviceFlag, keyword, pageable);
        DefaultPage<Workflow> page = new DefaultPage<Workflow>(workflows, pageable, total);

        logger.debug("Found   {}", page.getContent());

        return page;
    }

    @Override
    public Workflow findBySn(String sn, String serviceFlag) {

        logger.debug("Finding Workflow by sn: {}", sn);

        Workflow workflow = repository.findBySn(sn, serviceFlag);

        logger.debug("Found   {}", workflow);

        return workflow;
    }

}
