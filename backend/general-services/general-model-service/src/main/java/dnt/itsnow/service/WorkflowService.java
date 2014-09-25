package dnt.itsnow.service;

import dnt.itsnow.exception.WorkflowException;
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

    /**
     * 查找所有的工作流记录
     * @param keyword 查询关键字
     * @param pageable 分页实体类
     * @param serviceFlag 服务标识，1为私有服务目录，0为公有服务目录
     * @return
     */
    public Page<Workflow> findAll(String keyword, Pageable pageable, String serviceFlag);

    /**
     * 根据SN查询
     * @param sn 序列号
     * @param serviceFlag 服务标识，1为私有服务目录，0为公有服务目录
     * @return
     */
    public Workflow findBySn(String sn, String serviceFlag);
}
