/**
 * Developer: Kadvin Date: 14-9-21 下午4:45
 */
package dnt.itsnow.service;

import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.SystemInvocation;

/**
 * <h1>The system invoker</h1>
 */
public interface SystemInvoker {
    void invoke(SystemInvocation invocation) throws SystemInvokeException;
}
