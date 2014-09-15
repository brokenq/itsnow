/**
 * Developer: Kadvin Date: 14-9-15 下午1:55
 */
package dnt.itsnow.model;

import java.util.Properties;

/**
 * <h1>对应于 ItsnowProcess 的Client Json解析对象</h1>
 */
public class ClientItsnowProcess extends ClientRecord {
    private Integer    accountId;
    private Integer    hostId;
    private Integer    schemaId;
    private String     status;
    private Integer    pid;// Process ID
    private String     wd;  //Working dir
    private Properties configuration;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
