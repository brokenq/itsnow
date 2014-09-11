/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

/**
 * <h1>描述Group和Role之间的关联关系</h1>
 * <p/>
 */
public class UserRole {

    private Long id;
    private User user;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserRole{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
