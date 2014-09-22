/**
 * Developer: Kadvin Date: 14-8-27 下午12:17
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Properties;

/**
 * The itsnow process represents runtime instance of MSC/MSU/MSP
 */
public class ItsnowProcess extends ConfigItem{
    //Process的类型根据Account可以得知
    @NotNull
    private Long accountId;
    private Account account;
    @NotNull
    private Long hostId;
    private ItsnowHost host;
    @NotNull
    private Long schemaId;
    private ItsnowSchema schema;

    private ProcessStatus status;
    private Integer pid;// Process ID
    @NotNull
    private String wd;  //Working dir
    private Properties configuration;

    @NotNull
    // 由于name会用作url path中的identify，所以不能包括 .,/,\等
    @Pattern(regexp = "[\\w|\\d|\\-|_]+")
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public ItsnowHost getHost() {
        return host;
    }

    public void setHost(ItsnowHost host) {
        this.host = host;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public ItsnowSchema getSchema() {
        return schema;
    }

    public void setSchema(ItsnowSchema schema) {
        this.schema = schema;
    }

    public Long getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Long schemaId) {
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

    @JsonIgnore
    public String getHostAddress(){
        if( host != null ) return host.getAddress();
        return null;
    }

}
