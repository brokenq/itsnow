package dnt.itsnow.service;

import dnt.itsnow.Exception.WorkflowException;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

public interface MspWorkflowService {

    public Page<Workflow> findAll(String keyword, Pageable pageable);

    public Workflow findBySn(String sn);

    public Workflow create(Workflow workflow) throws WorkflowException;

    public Workflow update(Workflow workflow) throws WorkflowException;

    public Workflow destroy(Workflow workflow) throws WorkflowException;

}
