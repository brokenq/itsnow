package dnt.itsnow.model;

import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public class MscRole extends Role {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MscRole{");
        sb.append("users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}
