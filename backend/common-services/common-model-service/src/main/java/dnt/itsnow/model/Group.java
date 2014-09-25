/**
 * xiongjie on 14-7-23.
 */
package dnt.itsnow.model;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * <h1>The user group</h1>
 *
 * group作为配置项
 *
 * group#name == group#groupName
 *
 * TODO 添加测试用例
 * TODO add group to members support
 */
public class Group extends ConfigItem {

    @NotBlank
    private String sn;

    private List<GroupDetail> details;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<GroupDetail> getDetails() {
        return details;
    }

    public void setDetails(List<GroupDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Group{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", details=").append(details);
        sb.append('}');
        return sb.toString();
    }
}
