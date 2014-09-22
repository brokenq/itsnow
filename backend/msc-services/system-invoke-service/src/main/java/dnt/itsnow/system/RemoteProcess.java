/**
 * Developer: Kadvin Date: 14-9-20 上午11:25
 */
package dnt.itsnow.system;

import dnt.itsnow.model.RemoteInvocation;
import dnt.util.StringUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * <h1>Remote SSH Shell</h1>
 */
public class RemoteProcess extends AbstractProcess<RemoteInvocation> {

    public RemoteProcess(RemoteInvocation invocation, ExecutorService systemInvokeExecutor) {
        super(invocation, systemInvokeExecutor);
    }

    @Override
    protected String assembleCommand(String command, Object[] args) {
        List<String> remoteCommands = getCommands(command, args);
        String remoteCommand = StringUtils.join(remoteCommands, " ");
        return String.format("ssh root@%s \"cd %s && %s\"",
                             this.invocation.getHost(),
                             this.invocation.getWd(),
                             remoteCommand);
    }

}
