/**
 * Developer: Kadvin Date: 14-8-27 下午12:17
 */
package dnt.itsnow.model;

import java.util.Properties;

/**
 * The itsnow process represents runtime instance of MSC/MSU/MSP
 */
public class ItsnowProcess extends ConfigItem{
    //Process的类型根据Account可以得知
    private Account account;
    private ItsnowHost host;
    private ItsnowSchema schema;
    private ProcessStatus status;
    private Integer pid;// Process ID
    private String wd;  //Working dir
    private Properties configuration;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ItsnowHost getHost() {
        return host;
    }

    public void setHost(ItsnowHost host) {
        this.host = host;
    }

    public ItsnowSchema getSchema() {
        return schema;
    }

    public void setSchema(ItsnowSchema schema) {
        this.schema = schema;
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
}
