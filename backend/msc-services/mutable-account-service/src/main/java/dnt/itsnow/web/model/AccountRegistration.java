/**
 * @author XiongJie, Date: 14-9-2
 */
package dnt.itsnow.web.model;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.MspAccount;
import dnt.itsnow.model.MsuAccount;
import dnt.itsnow.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <h1>帐号注册信息</h1>
 */
@IndividualNotActsAsUser
@EnterpriseNotActsAsBoth// 暂时不支持，未来会支持
public class AccountRegistration {
    RegistrationType type;// enterprise, individual
    boolean asUser, asProvider;

    @NotNull
    @Valid
    Account account;
    @NotNull
    @Valid
    User user;

    public RegistrationType getType() {
        return type;
    }

    public void setType(RegistrationType type) {
        this.type = type;
    }

    public boolean isAsUser() {
        return asUser;
    }

    public void setAsUser(boolean asUser) {
        this.asUser = asUser;
    }

    public boolean isAsProvider() {
        return asProvider;
    }

    public void setAsProvider(boolean asProvider) {
        this.asProvider = asProvider;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account prepareAccount() {
        Account prepared;
        if(isAsUser()){
            prepared = new MsuAccount();
        }else {
            prepared = new MspAccount();
        }
        prepared.apply(getAccount());
        return prepared;
    }
}
