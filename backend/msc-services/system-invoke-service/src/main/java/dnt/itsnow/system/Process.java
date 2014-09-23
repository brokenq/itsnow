/**
 * Developer: Kadvin Date: 14-9-20 上午11:23
 */
package dnt.itsnow.system;

import dnt.itsnow.exception.SystemInvokeException;

import java.util.List;

/**
 * The system shell
 */
public interface Process {
    int run(String command, Object... args) throws SystemInvokeException;

    List<String> getCommand();

    String getOutput();

    String getError();
}
