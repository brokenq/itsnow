/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

/**
 * <h1>描述Site和Department之间的关联关系</h1>
 * <p/>
 */
public class SiteDept {

    private Long id;
    private Site site;
    private Department department;

    public SiteDept() {
    }

    public SiteDept(Site site, Department department) {
        this.site = site;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
