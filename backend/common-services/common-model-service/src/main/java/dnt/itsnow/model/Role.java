package dnt.itsnow.model;

import java.util.List;

/**
 * <h1>角色对象</h1>
 * <p/>
 */
public class Role extends ConfigItem {

    public final static String ROLE_ = "ROLE_";

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Role{");
        sb.append("users=").append(users);
        sb.append('}');
        return sb.toString();
    }

}
