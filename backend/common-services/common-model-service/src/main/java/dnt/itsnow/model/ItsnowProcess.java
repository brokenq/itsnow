/**
 * Developer: Kadvin Date: 14-8-27 下午12:17
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.happyonroad.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * The itsnow process represents runtime instance of MSC/MSU/MSP
 */
public class ItsnowProcess extends DeployResource{
    //Process的类型根据Account可以得知
    @NotNull
    private Long accountId;
    private Account account;
    @NotNull
    private Long hostId;
    private ItsnowHost host;

    private Long schemaId;
    private ItsnowSchema schema;

    private ProcessStatus status;
    private Integer pid;// Process ID
    @NotNull
    private String wd;  //Working dir

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
        if( account != null ) setAccountId(account.getId());
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
        if( host != null ) setHostId(host.getId());
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
        if(schema != null) setSchemaId(schema.getId());
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

    @JsonIgnore
    public String getHostAddress(){
        if( host != null ) return host.getAddress();
        return null;
    }

    @JsonIgnore
    public String getDisplayName() {
        return StringUtils.capitalize(getName());
    }

    @JsonIgnore
    public String getIdentifier() {
        return getName().toLowerCase().replaceFirst("itsnow[_|-]", "");
    }
}
