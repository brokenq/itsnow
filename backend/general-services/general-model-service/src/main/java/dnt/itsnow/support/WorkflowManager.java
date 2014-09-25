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

import java.sql.Timestamp;
import java.util.List;

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

    @Override
    public Page<Workflow> findAll(String keyword, Pageable pageable, String serviceFlag) {
        logger.debug("Finding workflow by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = workflowRepository.count();
            List<Workflow> workflows = workflowRepository.find(serviceFlag,"updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Workflow>(workflows, pageable, total);
        }else{
            int total = workflowRepository.countByKeyword("%"+keyword+"%");
            List<Workflow> workflows = workflowRepository.findByKeyword(serviceFlag,"%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Workflow>(workflows, pageable, total);
        }
    }

    @Override
    public Workflow findBySn(String sn, String serviceFlag) {
        logger.debug("Finding Workflow by sn: {}", sn);

        return workflowRepository.findBySn(sn, serviceFlag);
    }
}
