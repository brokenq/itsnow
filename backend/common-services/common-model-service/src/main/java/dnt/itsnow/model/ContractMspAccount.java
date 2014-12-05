package dnt.itsnow.model;

import java.sql.Timestamp;

/**
 * <h1>合同管理中有批准状态的服务供应方账号</h1>
 */
public class ContractMspAccount extends MspAccount {

    /** MSU在合同中对此MSP账户的操作状态（批准或拒绝） */
    private ContractStatus contractStatus;

    /** 合同应约时间 */
    private Timestamp createdAt;
    /** 合同被批准或拒绝时间 */
    private Timestamp updatedAt;

    @Override
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

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
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
