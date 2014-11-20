package dnt.itsnow.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>合同与MSP用户间的关系</h1>
 */
public class ContractUser {

    private Contract contract;

    private List<User> users;

    private String accountSn;

    private String access;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContractUser{");
        sb.append("contract=").append(contract);
        sb.append(", users=").append(users);
        sb.append(", accountSn='").append(accountSn).append('\'');
        sb.append(", access='").append(access).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getAccountSn() {
        return accountSn;
    }

    public void setAccountSn(String accountSn) {
        this.accountSn = accountSn;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
