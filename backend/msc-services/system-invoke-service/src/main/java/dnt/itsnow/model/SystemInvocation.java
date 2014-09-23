/**
 * Developer: Kadvin Date: 14-9-15 下午4:55
 */
package dnt.itsnow.model;

import dnt.itsnow.system.Process;



/**
 * <h1>进行系统调用的任务</h1>
 */
public abstract class SystemInvocation {
    private String id; // 任务标识符
    private int    seq;   // 任务序号
    private long timeout = 1000 * 60 * 5; // 任务超时(单位ms)
    private SystemInvocation next;
    private String           wd;
    private String           command;

    public SystemInvocation(String wd) {
        this.wd = wd;
        this.seq = 0;
    }

    public String getWd() {
        return wd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public abstract int perform(Process process) throws Exception;

    public SystemInvocation next(SystemInvocation invocation) {
        this.next = invocation;
        this.next.seq = this.seq + 1;
        return this.next;
    }

    public SystemInvocation getNext() {
        return next;
    }

    @Override
    public String toString() {
        return String.format("%s@%d\nroot@%s:%s\n\n", getId(), seq, wd, this.command);
    }

    public void recordCommand(String command) {
        this.command = command;
    }

    public String getOutFileName(){
        return getId() + "@" + seq + ".out";
    }

    public String getErrFileName(){
        return getId() + "@" + seq + ".err";
    }
}
