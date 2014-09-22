/**
 * Developer: Kadvin Date: 14-9-20 上午11:24
 */
package dnt.itsnow.system;

import dnt.itsnow.model.LocalInvocation;
import dnt.util.StringUtils;

import java.io.File;
import java.util.concurrent.ExecutorService;

/**
 * <h1>Local Shell</h1>
 */
public class LocalProcess extends AbstractProcess<LocalInvocation> {


    public LocalProcess(LocalInvocation invocation, ExecutorService systemInvokeExecutor) {
        super(invocation, systemInvokeExecutor);
    }

    @Override
    protected String assembleCommand(String command, Object[] args) {
        builder.directory(new File(invocation.getWd()));
        return StringUtils.join(getCommands(command, args), " ");
    }
}
