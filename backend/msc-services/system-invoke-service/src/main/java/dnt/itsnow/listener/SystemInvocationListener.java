/**
 * Developer: Kadvin Date: 14-9-18 上午9:08
 */
package dnt.itsnow.listener;

import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.SystemInvocation;

import java.util.EventListener;

/**
 * <h1>系统任务执行情况的监听器</h1>
 */
public interface SystemInvocationListener extends EventListener {
    /**
     * 当一个系统调用被加入到系统任务中时，本监听者将会被通过该接口通知到
     *
     * @param invocation 调用
     */
    void added(SystemInvocation invocation);

    /**
     * 当一个系统调用的线程被实际启动时，本监听者将会被通过该接口通知到
     *
     * @param invocation 调用
     */
    void started(SystemInvocation invocation);

    /**
     * 当一个系统调用完成时，本监听者将会被通过该接口通知到
     *
     * @param invocation 调用
     */
    void finished(SystemInvocation invocation);

    /**
     * 当一个系统调用被取消时，本监听者将会被通过该接口通知到
     *
     * @param invocation 调用
     */
    void cancelled(SystemInvocation invocation);

    /**
     * 当一个系统调用失败时，本监听者将会被通过该接口通知到
     *
     * @param invocation 调用
     * @param e exception
     */
    void failed(SystemInvocation invocation, SystemInvokeException e);
}
