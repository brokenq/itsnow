/**
 * Developer: Kadvin Date: 14-9-15 下午4:55
 */
package dnt.itsnow.model;

import dnt.itsnow.system.Process;
import dnt.util.StringUtils;


/**
 * <h1>进行系统调用的任务</h1>
 */
public abstract class SystemInvocation {
    protected String id; // 任务标识符
    protected int    seq;   // 任务序号
    private long timeout ; // 任务超时(单位ms)
    protected           SystemInvocation next;
    protected           String           wd;
    protected transient Process          process;

    public SystemInvocation(String wd) {
        this.wd = wd;
        this.seq = 0;
        setTimeout(1000 * 60 * 5);
    }

    public String getWd() {
        return wd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        if(this.next != null ) this.next.setId(id);
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long totalTimeout() {
        if( this.next == null ) return getTimeout();
        return getTimeout() + this.next.totalTimeout();
    }

    public abstract int perform(Process process) throws Exception;

    //
    // 把该invocation放到chain的末尾
    // 作用上类似于append
    public SystemInvocation next(SystemInvocation invocation) {
        if (this.next == null) {
            this.next = invocation;
            this.next.id = this.id;
            this.next.seq = this.seq + 1;
        } else {
            this.next.next(invocation);
        }
        return this;
    }

    public SystemInvocation getNext() {
        return next;
    }

    public String getTotalFileName() {
        return getId() + ".log";
    }

    public String getOutFileName() {
        return getId() + "@" + seq + ".out";
    }

    public String getErrFileName() {
        return getId() + "@" + seq + ".err";
    }

    public void bind(Process process) {
        this.process = process;
    }

    public Process getProcess() {
        return this.process;
    }

    protected String getCommand(){
        if( process == null ) return "<no execution>";
        return StringUtils.join(process.getCommand(), " ");
    }

    public int getSequence() {
        return seq;
    }
}
