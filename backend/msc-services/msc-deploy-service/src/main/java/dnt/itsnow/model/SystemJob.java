/**
 * Developer: Kadvin Date: 14-9-15 下午4:55
 */
package dnt.itsnow.model;

/**
 * <h1>进行系统调用的任务</h1>
 */
public class SystemJob {
    private ItsnowHost host;
    private String wd; //调用时的工作目录
    private String command;//调用执行的命令
    private String output; //输出文件

    public SystemJob(ItsnowHost host, String wd, String command) {
        this.host = host;
        this.wd = wd;
        this.command = command;
    }

    public ItsnowHost getHost() {
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
}
