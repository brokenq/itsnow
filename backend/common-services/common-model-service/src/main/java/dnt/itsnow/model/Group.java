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

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + super.getId() +
                ", sn='" + getSn() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", createdAt=" + super.getCreatedAt() +
                ", updatedAt=" + super.getUpdatedAt() +
                '}';
    }
}
