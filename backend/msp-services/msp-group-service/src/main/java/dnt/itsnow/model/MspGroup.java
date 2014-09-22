package dnt.itsnow.model;

/**
 * <h1>类功能说明</h1>
 */
public class MspGroup extends Group {

    public User user;
    public Department department;
    public Site site;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MspGroup{");
        sb.append("user=").append(user);
        sb.append(", department=").append(department);
        sb.append(", site=").append(site);
        sb.append('}');
        return sb.toString();
    }
}
