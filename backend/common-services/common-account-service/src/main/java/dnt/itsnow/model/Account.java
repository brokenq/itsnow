/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;

/**
 * <h1>>服务采购方(MSU)或者服务供应方(MSP)在系统数据库中的账户</h1
 */
public class Account extends Record {
    // 帐户名称
    private String name;
    // 账户状态
    private AccountStatus status;

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
}
