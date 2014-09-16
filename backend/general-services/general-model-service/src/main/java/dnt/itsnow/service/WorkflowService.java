package dnt.itsnow.service;

import dnt.itsnow.exception.WorkflowException;
import dnt.itsnow.model.Workflow;

/**
 * <h1>类功能说明</h1>
 */
public interface WorkflowService {

    public Workflow create(Workflow workflow) throws WorkflowException;

    public Workflow update(Workflow workflow) throws WorkflowException;

    public Workflow destroy(Workflow workflow) throws WorkflowException;

}
