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
import java.io.File;
import java.util.Map;

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

    Map<String, File> attachments;

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

    public Map<String, File> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, File> attachments) {
        this.attachments = attachments;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountRegistration)) return false;

        AccountRegistration that = (AccountRegistration) o;

        if (asProvider != that.asProvider) return false;
        if (asUser != that.asUser) return false;
        if (!account.equals(that.account)) return false;
        if (type != that.type) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (asUser ? 1 : 0);
        result = 31 * result + (asProvider ? 1 : 0);
        result = 31 * result + account.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
