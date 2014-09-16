/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dnt.itsnow.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <h1>MSU和MSP之间关于服务的合同契约</h1>
 *
 * 合同暂时不作为配置项
 *
 * TODO 添加测试用例
 */
public class Contract extends Record {
    //合同编号
    @NotBlank
    private String sn;
    //合同甲方，服务采购方
    @NotNull
    private Long msuAccountId;
    //@JsonIgnore
    private MsuAccount msuAccount;
    //合同乙方，服务供应方
    //@NotNull
    private Long mspAccountId;
    //@JsonIgnore
    private MspAccount mspAccount;
    // MSU 是否批准
    @NotNull
    private ContractStatus msuStatus;
    // MSP 是否批准
    @NotNull
    private ContractStatus mspStatus;

    //合同明细
    @JsonIgnore
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

    @JsonIgnore
    public boolean isApprovedByMsu() {
        return msuStatus.isApproved();
    }

    @JsonIgnore
    public boolean isApprovedByMsp() {
        return mspStatus.isApproved();
    }
}
