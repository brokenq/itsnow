package dnt.itsnow.model;

/**
 * <h1>合同与MSP用户间的关系</h1>
 */
public class ContractUser {

    private Contract contract;
    private User user;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContractUser{");
        sb.append("contract=").append(contract);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
