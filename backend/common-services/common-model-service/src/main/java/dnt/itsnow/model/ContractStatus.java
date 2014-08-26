/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.model;

/**
 * <h1>Enum Usage</h1>
 */
public enum ContractStatus {
    //草案 -> 提议 -> 批准 | 拒绝
    Draft, Purposed, Approved, Rejected;

    public boolean isApproved() {
        return this == Approved;
    }
}
