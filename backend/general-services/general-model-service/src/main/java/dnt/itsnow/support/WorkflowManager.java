package dnt.itsnow.support;

import dnt.itsnow.exception.WorkflowException;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.repository.WorkflowRepository;
import dnt.itsnow.service.WorkflowService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <h1>类功能说明</h1>
 */
@Service
public class WorkflowManager extends Bean implements WorkflowService {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Override
    public Workflow create(Workflow workflow) throws WorkflowException {
        logger.info("Creating workflow {}", workflow);
        if (workflow == null) {
            throw new WorkflowException("Workflow entry can not be empty.");
        }
        workflow.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        workflow.setUpdatedAt(workflow.getCreatedAt());
        workflowRepository.create(workflow);

        return workflow;
    }

    @Override
    public Workflow update(Workflow workflow) throws WorkflowException {
        logger.info("Updating workflow {}", workflow);
        if (workflow == null) {
            throw new WorkflowException("Workflow entry can not be empty.");
        }
        workflowRepository.update(workflow);

        return workflow;
    }

    @Override
    public Workflow destroy(Workflow workflow) throws WorkflowException {
        logger.warn("Deleting workflow {}", workflow);
        if (workflow == null) {
            throw new WorkflowException("Workflow entry can not be empty.");
        }
        workflowRepository.delete(workflow.getSn());
        return workflow;
    }
}
