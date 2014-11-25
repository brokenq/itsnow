/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dnt.itsnow.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * <h1>MSU和MSP之间关于服务的合同契约</h1>
 *
 * 合同暂时不作为配置项
 *
 */
public class Contract extends Record {

    // 合同编号
    @NotBlank
    private String sn;
    // 合同甲方，服务采购方
    private Long msuAccountId;
    // @JsonIgnore
    private MsuAccount msuAccount;
    //合同乙方，服务供应方
    private Long mspAccountId;
    // @JsonIgnore
    private MspAccount mspAccount;
    // MSU 是否批准
    private ContractStatus msuStatus;
    // MSP 是否批准
    private ContractStatus mspStatus;
    //合同明细
    private List<ContractDetail> details;
    // 与MSU签订合同的MSP用户列表，其中包含是否允许登录MSU信息
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public ContractStatus getMsuStatus() {
        return msuStatus;
    }

    public void setMsuStatus(ContractStatus msuStatus) {
        this.msuStatus = msuStatus;
    }

    public ContractStatus getMspStatus() {
        return mspStatus;
    }

    public void setMspStatus(ContractStatus mspStatus) {
        this.mspStatus = mspStatus;
    }

    public Long getMsuAccountId() {
        return msuAccountId;
    }

    public void setMsuAccountId(Long msuAccountId) {
        this.msuAccountId = msuAccountId;
    }

    public Long getMspAccountId() {
        return mspAccountId;
    }

    public void setMspAccountId(Long mspAccountId) {
        this.mspAccountId = mspAccountId;
    }

    public MsuAccount getMsuAccount() {
        return msuAccount;
    }

    public void setMsuAccount(MsuAccount msuAccount) {
        this.msuAccount = msuAccount;
    }

    public MspAccount getMspAccount() {
        return mspAccount;
    }

    public void setMspAccount(MspAccount mspAccount) {
        this.mspAccount = mspAccount;
    }

    public List<ContractDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ContractDetail> details) {
        this.details = details;
    }

    public ContractDetail getDetail(Long id) {
        if (details == null) return null;
        for (ContractDetail detail : details) {
            if (detail.getId().equals(id)) return detail;
        }
        return null;
    }

    @JsonIgnore
    public boolean isApprovedByMsu() {
        return msuStatus.isApproved();
    }

    @JsonIgnore
    public boolean isApprovedByMsp() {
        return mspStatus.isApproved();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contract{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", msuAccountId=").append(msuAccountId);
        sb.append(", msuAccount=").append(msuAccount);
        sb.append(", mspAccountId=").append(mspAccountId);
        sb.append(", mspAccount=").append(mspAccount);
        sb.append(", msuStatus=").append(msuStatus);
        sb.append(", mspStatus=").append(mspStatus);
        sb.append(", details=").append(details);
        sb.append(", users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}
