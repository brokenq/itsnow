/**
 * Developer: Kadvin Date: 14-8-27 下午12:17
 */
package dnt.itsnow.model;

import javax.validation.constraints.NotNull;
import java.util.Properties;

/**
 * The itsnow process represents runtime instance of MSC/MSU/MSP
 */
public class ItsnowProcess extends ConfigItem{
    //Process的类型根据Account可以得知
    @NotNull
    private Integer accountId;
    private Account account;
    @NotNull
    private Integer hostId;
    private ItsnowHost host;
    @NotNull
    private Integer schemaId;
    private ItsnowSchema schema;

    private ProcessStatus status;
    private Integer pid;// Process ID
    @NotNull
    private String wd;  //Working dir
    private Properties configuration;

    @NotNull
    @Override
    public String getName() {
        return super.getName();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public ItsnowHost getHost() {
        return host;
    }

    public void setHost(ItsnowHost host) {
        this.host = host;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public ItsnowSchema getSchema() {
        return schema;
    }

    public void setSchema(ItsnowSchema schema) {
        this.schema = schema;
    }

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }

    public SystemJob createStartJob() {
        return new SystemJob(getHost(), getWd(), "bin/start.sh");
    }

    public SystemJob createStopJob() {
        return new SystemJob(getHost(), getWd(), "bin/stop.sh");
    }

    public SystemJob createDeployJob() {
        // null 代表在系统默认目录
        return new SystemJob(getHost(), null, "system/deploy.sh " + getWd() + " " + getName());
    }

    public SystemJob createUndeployJob() {
        // null 代表在系统默认目录
        return new SystemJob(getHost(), null, "system/undeploy.sh " + getWd() + " " + getName());
    }
}
