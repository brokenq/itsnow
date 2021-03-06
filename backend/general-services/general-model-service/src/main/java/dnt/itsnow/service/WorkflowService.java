package dnt.itsnow.service;

import dnt.itsnow.exception.WorkflowException;
import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>流程管理业务接口层</h1>
 */
public interface WorkflowService {

    /**
     * 创建一个工作流配置信息
     * @param workflow 工作流配置信息实体类
     * @return 工作流配置信息
     * @throws WorkflowException
     */
    public Workflow create(Workflow workflow) throws WorkflowException;

    /**
     * 修改一个工作流配置信息
     * @param workflow 工作流配置信息实体类
     * @return 工作流配置信息
     * @throws WorkflowException
     */
    public Workflow update(Workflow workflow) throws WorkflowException;

    /**
     * 销毁一个工作流配置信息
     * @param workflow 工作流配置信息实体类
     * @throws WorkflowException
     */
    public void destroy(Workflow workflow) throws WorkflowException;

    /**
     * 查找所有的工作流配置信息
     * @param keyword 查询关键字
     * @param pageable 分页实体类
     * @param serviceFlag 服务标识，1为私有服务目录，0为公有服务目录
     * @return 工作流配置信息分页列表
     */
    public Page<Workflow> findAll(String keyword, Pageable pageable, String serviceFlag);

    /**
     * 根据SN查询工作流配置信息
     * @param sn 序列号
     * @param serviceFlag 服务标识，1为私有服务目录，0为公有服务目录
     * @return 工作流配置信息
     */
    public Workflow findBySn(String sn, String serviceFlag);
}
