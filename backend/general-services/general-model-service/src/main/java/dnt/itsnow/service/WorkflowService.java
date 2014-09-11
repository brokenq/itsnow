package dnt.itsnow.service;

import dnt.itsnow.Exception.WorkflowException;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>类功能说明</h1>
 */
public interface WorkflowService {

    public Workflow create(Workflow workflow) throws WorkflowException;

    public Workflow update(Workflow workflow) throws WorkflowException;

    public Workflow destroy(Workflow workflow) throws WorkflowException;

}
