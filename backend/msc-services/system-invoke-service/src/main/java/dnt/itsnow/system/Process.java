/**
 * Developer: Kadvin Date: 14-9-20 上午11:23
 */
package dnt.itsnow.system;

import dnt.itsnow.exception.SystemInvokeException;

/**
 * The system shell
 */
public interface Process {
    int run(String command, Object... args) throws SystemInvokeException;

    String getOutput();

    String getError();
}
