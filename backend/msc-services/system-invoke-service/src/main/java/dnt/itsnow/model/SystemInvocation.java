/**
 * Developer: Kadvin Date: 14-9-15 下午4:55
 */
package dnt.itsnow.model;

import java.util.Properties;

/**
 * <h1>进行系统调用的任务</h1>
 */
public class SystemInvocation {
    private       String     id; // 任务标识符
    private       String     host;
    private       String     wd; //调用时的工作目录
    private       String     command;//调用执行的命令
    private       String     output; //输出文件
    private final Properties properties;

    private long timeout = 1000 * 60 * 5; // 任务超时(单位ms)

    public SystemInvocation(String host, String wd, String command, Properties... properties) {
        this.host = host;
        this.wd = wd;
        this.command = command;
        this.properties = new Properties();
        //这些属性已经增加了 host. process. schema. 等前缀
        for (Properties props : properties) {
            this.properties.putAll(props);
        }
    }

    public String getHost() {
        return host;
    }

    public String getWd() {
        return wd;
    }

    public String getCommand() {
        return command;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
