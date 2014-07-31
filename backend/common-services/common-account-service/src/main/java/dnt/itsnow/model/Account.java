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
}
