package dnt.itsnow.service;

import dnt.itsnow.model.Incident;
import dnt.itsnow.model.MspIncident;
import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.PageRequest;

import java.io.InputStream;

/**
 * <h1>Msp Incident Service</h1>
 */
public interface MspIncidentService {

    /**
     * <h2>根据用户名和流程KEY查询故障列表</h2>
     *
     * @param username  用户名
     * @param keyword  流程定义KEY
     * @param pageable 分页请求
     * @return 故障分页数据
     */
    Page<Incident> findByUserAndKey(String username, String keyword, Pageable pageable);

    /**
     * <h2>根据用户名和流程KEY查询该用户创建的故障列表</h2>
     *
     * @param username  用户名
     * @param keyword  流程定义KEY
     * @param pageable 分页请求
     * @return 已关闭故障分页数据
     */
    Page<Incident> findAllCreatedByUserAndKey(String username, String keyword, Pageable pageable);


    /**
     * <h2>根据用户名和流程KEY查询已关闭故障列表</h2>
     *
     * @param username  用户名
     * @param keyword  流程定义KEY
     * @param pageable 分页请求
     * @return 已关闭故障分页数据
     */
    Page<Incident> findClosedByUserAndKey(String username, String keyword, Pageable pageable);

    /**
     * <h2>根据流程实例ID查询故障</h2>
     *
     * @param instanceId  流程实例ID
     * @param withHistory  是否返回历史数据
     * @return 单条故障数据或者null
     */
    MspIncident findByInstanceId(String instanceId,boolean withHistory);

    /**
     * <h2>启动故障流程</h2>
     *
     * @param accountName  帐户名
     * @param username  用户名
     * @param incident  故障表单数据
     * @return 故障表单
     */
    MspIncident startIncident(String accountName,String username,Incident incident);

    /**
     * <h2>处理故障流程，可执行的动作包括签收，分析，解决，关闭</h2>
     *
     * @param instanceId  流程实例ID
     * @param taskId    任务ID
     * @param username  用户名
     * @param incident  故障表单数据
     * @return 故障表单数据以及任务信息
     */
    MspIncident processIncident(String instanceId,String taskId,String username,Incident incident);

    /**
     * 获取流程图数据
     * @param instanceId 流程实例ID
     * @return  图片stream
     */
    InputStream getProcessImage(String instanceId);

    /**
     * 获取所有监控的故障单
     * @param key 关键字
     * @param pageRequest 分页信息
     * @return 故障单列表
     */
    Page<Incident> findMonitoredByKey(String key, PageRequest pageRequest);

    /**
     * 抢单，根据msuInstanceId生成msp Incident
     * @param id msp_incident id
     * @param currentUser 当前用户
     * @return MspIncident
     */
    MspIncident grabIncident(String id, User currentUser);
}
