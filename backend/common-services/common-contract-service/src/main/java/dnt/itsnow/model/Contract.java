/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * <h1>MSU和MSP之间关于服务的合同契约</h1>
 *
 * 合同暂时不作为配置项
 */
public class Contract extends Record {
    //合同编号
    @NotBlank
    private String sn;
    //合同甲方，服务采购方
    @NotBlank
    private Long msuAccountId;
    private MsuAccount msuAccount;
    //合同乙方，服务供应方
    @NotBlank
    private Long mspAccountId;
    private MspAccount mspAccount;
    // MSU 是否批准
    @NotBlank
    private ContractStatus msuStatus;
    // MSP 是否批准
    @NotBlank
    private ContractStatus mspStatus;

    //合同明细
    private List<ContractDetail> details;

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

    public boolean isApprovedByMsu() {
        return msuStatus.isApproved();
    }

    public boolean isApprovedByMsp() {
        return mspStatus.isApproved();
    }
}
