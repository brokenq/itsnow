/**
 * Developer: Kadvin Date: 14-9-20 上午11:21
 */
package dnt.itsnow.model;

import dnt.util.StringUtils;

/**
 * <h1>对远程主机的调用</h1>
 */
public abstract class RemoteInvocation extends SystemInvocation{
    private final String host;

    public RemoteInvocation(String address) {
        this(address, "/opt/system/script");
    }

    public RemoteInvocation(String address, String wd) {
        super(wd);
        this.host = address;
    }

    public String getHost() {
        return host;
    }

    @Override
    public String toString() {
        String command = getCommand();
        if(StringUtils.isBlank(command)){
            command = "<no execution>";
        }
        if( !command.equals("<no execution>") ) return command;
        return String.format("%s@%d\nroot@%s:%s %s\n\n", id, seq, host, wd, command);
    }
}
