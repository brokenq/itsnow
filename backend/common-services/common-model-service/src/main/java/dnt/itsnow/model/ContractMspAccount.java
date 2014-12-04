package dnt.itsnow.model;

/**
 * <h1>合同管理中有批准状态的服务供应方账号</h1>
 */
public class ContractMspAccount extends MspAccount {

    /* MSU在合同中对此MSP账户的操作状态（批准或拒绝） */
    private ContractStatus contractStatus;

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContractMspAccount{");
        sb.append("contractStatus=").append(contractStatus);
        sb.append('}');
        return sb.toString();
    }
}
