/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

/**
 * <h1>描述Group和User之间的关联关系</h1>
 * <p/>
 */
public class GroupUser {

    private Long id;
    private Group group;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
