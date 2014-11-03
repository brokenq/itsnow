/**
 * Developer: Kadvin Date: 14-9-15 下午1:55
 */
package dnt.itsnow.model;

import com.sun.istack.internal.NotNull;

import java.util.Properties;

/**
 * <h1>对应于 ItsnowProcess 的Client Json解析对象</h1>
 */
public class ClientItsnowProcess extends ClientConfigItem{
    @NotNull
    private Long accountId;
    private ClientAccount account;
    @NotNull
    private Long hostId;
    private ClientItsnowHost host;

    private Long schemaId;
    private ClientItsnowSchema schema;

    private ClientProcessStatus status;
    private Integer pid;// Process ID
    @NotNull
    private String wd;  //Working dir
    private Properties configuration;

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public ClientAccount getAccount() {
        return account;
    }

    public void setAccount(ClientAccount account) {
        this.account = account;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public ClientItsnowHost getHost() {
        return host;
    }

    public void setHost(ClientItsnowHost host) {
        this.host = host;
    }

    public Long getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Long schemaId) {
        this.schemaId = schemaId;
    }

    public ClientItsnowSchema getSchema() {
        return schema;
    }

    public void setSchema(ClientItsnowSchema schema) {
        this.schema = schema;
    }

    public ClientProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ClientProcessStatus status) {
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
}
