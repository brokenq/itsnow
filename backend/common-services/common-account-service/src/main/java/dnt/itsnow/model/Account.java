/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * <h1>>服务采购方(MSU)或者服务供应方(MSP)在系统数据库中的账户</h1
 */
public abstract class Account extends Record {
    public static final String MSC = "msc";
    public static final String MSU = "msu";
    public static final String MSP = "msp";

    private String        sn;
    @NotBlank
    // 帐户名称
    @Min(4)
    @Max(50)
    private String        name;
    // 账户状态
    private AccountStatus status = AccountStatus.New;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isExpired() {
        return this.status == AccountStatus.Expired;
    }

    public boolean isValid() {
        return this.status == AccountStatus.Valid;
    }

    public abstract String getType();

    public boolean isMsc(){
        return MSC.equals(getType());
    }

    public boolean isMsu(){
        return MSU.equals(getType());
    }

    public boolean isMsp(){
        return MSP.equals(getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Account account = (Account) o;

        if (name != null ? !name.equals(account.name) : account.name != null) return false;
        if (sn != null ? !sn.equals(account.sn) : account.sn != null) return false;
        //noinspection RedundantIfStatement
        if (status != account.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sn != null ? sn.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
