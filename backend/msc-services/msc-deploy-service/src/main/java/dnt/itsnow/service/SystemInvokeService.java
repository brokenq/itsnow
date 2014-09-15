/**
 * Developer: Kadvin Date: 14-9-15 下午4:14
 */
package dnt.itsnow.service;

import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.SystemJob;

/**
 * <h1>系统调用服务</h1>
 * TODO 本模块，包括模型，可能需要抽取到平台中
 */
public interface SystemInvokeService {
    /**
     * 调度一个系统调用的任务
     * @param job 任务信息
     * @return 任务描述符
     */
    String addJob(SystemJob job);

    /**
     * 取消执行某个任务
     * @param job 任务描述符
     */
    void cancelJob(String job);

    /**
     * 判断某个任务是否已经完成
     * @param job 任务描述符
     * @return 是否完成
     */
    boolean isFinished(String job);

    /**
     * 读取特定任务的输出信息
     *
     * @param job 任务描述符
     * @param offset 开始位置
     * @return 读到的内容
     */
    String[] read(String job, int offset);

    /**
     * 等待任务结束
     * @param job 任务描述符
     */
    void waitJobFinished(String job) throws SystemInvokeException;
}
