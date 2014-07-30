/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;

import java.util.List;

/**
 * <h1>MSU和MSP之间关于服务的合同契约</h1>
 */
public class Contract extends Record {
    //合同甲方，服务采购方
    private MsuAccount msuAccount;
    //合同乙方，服务供应方
    private MspAccount mspAccount;
    //合同明细
    private List<ContractDetail> details;

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
        if(details == null ) return null;
        for (ContractDetail detail : details) {
            if(detail.getId().equals(id)) return detail;
        }
        return null;
    }
}
