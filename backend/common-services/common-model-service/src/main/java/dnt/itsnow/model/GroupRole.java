/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

/**
 * <h1>描述Group和Role之间的关联关系</h1>
 * <p/>
 */
public class GroupRole {

    private Long id;
    private Group group;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
