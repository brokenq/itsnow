/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dnt.itsnow.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Arrays;

/**
 * <h1>>服务采购方(MSU)或者服务供应方(MSP)在系统数据库中的账户</h1
 */
public class Account extends AbstractAccount {
    public static final String[] RESERVED_DOMAINS = {"www", "blog", "dev", "developer", "sales", "marketing", "hr", "test"};

    public static final String MSC = "msc";
    public static final String MSU = "msu";
    public static final String MSP = "msp";


    //序号, auto number service generate
    // auto generate, controller without
    private String sn;
    //子域名
    @NotBlank
    @Pattern(regexp = "[\\w|\\d|_|-]+")
    @IsNotReservedDomain
    private String domain;
    private Long   userId; // 帐户管理员ID
    private User   user;   // 帐户管理员
    private ItsnowProcess process;
    //@NotBlank
    // 账户状态
    private AccountStatus status = AccountStatus.New;

    // 帐户名称
    @NotBlank
    @Size(min = 4, max = 50)
    public String getName() {
        return super.getName();
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @JsonIgnore
    public boolean isNew() {
        return this.status == AccountStatus.New;
    }

    @JsonIgnore
    public boolean isValid() {
        return this.status == AccountStatus.Valid;
    }

    @JsonIgnore
    public boolean isRejected() {
        return this.status == AccountStatus.Rejected;
    }

    public String getType(){
        return "base";
    }

    @JsonIgnore
    public boolean isMsc(){
        return MSC.equals(getType());
    }

    @JsonIgnore
    public boolean isMsu(){
        return MSU.equals(getType());
    }

    @JsonIgnore
    public boolean isMsp(){
        return MSP.equals(getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Account account = (Account) o;

        if (sn != null ? !sn.equals(account.sn) : account.sn != null) return false;
        if (userId != null ? !userId.equals(account.userId) : account.userId != null) return false;
        //noinspection RedundantIfStatement
        if (status != account.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sn != null ? sn.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public void apply(Record another) {
        super.apply(another);
        if (this.userId == 0) this.userId = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if(this.user == null ) this.userId = null;
        else this.userId = this.user.getId();
    }

    public ItsnowProcess getProcess() {
        return process;
    }

    public void setProcess(ItsnowProcess process) {
        this.process = process;
    }

    static {
        Arrays.sort(RESERVED_DOMAINS);
    }
}
