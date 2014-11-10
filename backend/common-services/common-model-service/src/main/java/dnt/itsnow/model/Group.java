package dnt.itsnow.model;

import java.util.List;

/**
 * <h1>The user group</h1>
 *
 * group作为配置项
 *
 * group#name == group#groupName
 *
 */
public class Group extends ConfigItem {

    private String sn;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Group{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", description='").append(getDescription()).append('\'');
        sb.append(", created_at='").append(getCreatedAt()).append('\'');
        sb.append(", update_at='").append(getUpdatedAt()).append('\'');
        sb.append(", users='").append(users).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
