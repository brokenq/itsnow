/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.happyonroad.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * <h1>MSU和MSP之间关于服务的合同契约</h1>
 *
 * 合同暂时不作为配置项
 *
 */
public class Contract extends Record {

    // 合同甲方，服务采购方
    private Long msuAccountId;
    // @JsonIgnore
    private MsuAccount msuAccount;
    // 合同编号
    @NotBlank
    private String sn;
    // 合同名称
    private String title;
    // 合同类型（从数据字典中取）
    private String type;
    // 合同状态
    private ContractStatus status;
    //合同明细
    private List<ContractDetail> details;
    // 与MSU签订合同的MSP用户列表，其中包含是否允许登录MSU信息
    private List<User> users;
    // MSP账户
    private List<MspAccount> mspAccounts;

    public List<MspAccount> getMspAccounts() {
        return mspAccounts;
    }

    public void setMspAccounts(List<MspAccount> mspAccounts) {
        this.mspAccounts = mspAccounts;
    }

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

    public Long getMsuAccountId() {
        return msuAccountId;
    }

    public void setMsuAccountId(Long msuAccountId) {
        this.msuAccountId = msuAccountId;
    }

    public MsuAccount getMsuAccount() {
        return msuAccount;
    }

    public void setMsuAccount(MsuAccount msuAccount) {
        this.msuAccount = msuAccount;
    }

    public List<ContractDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ContractDetail> details) {
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ContractDetail getDetail(Long id) {
        if (details == null) return null;
        for (ContractDetail detail : details) {
            if (detail.getId().equals(id)) return detail;
        }
        return null;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    @JsonIgnore
    public boolean isApproved() {
        return status.isApproved();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contract{");
        sb.append("msuAccountId=").append(msuAccountId);
        sb.append(", msuAccount=").append(msuAccount);
        sb.append(", sn='").append(sn).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", status=").append(status);
        sb.append(", details=").append(details);
        sb.append(", users=").append(users);
        sb.append(", mspAccounts=").append(mspAccounts);
        sb.append('}');
        return sb.toString();
    }
}
