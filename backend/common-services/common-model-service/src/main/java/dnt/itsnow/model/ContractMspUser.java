package dnt.itsnow.model;

/**
 * <h1>合同管理中的MSP用户</h1>
 */
public class ContractMspUser extends User {
private String access;

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContractMspUser{");
        sb.append("access='").append(access).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
