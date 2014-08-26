/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import java.util.Set;

/**
 * <h1>MSU/MSP的部门</h1>
 * 也是配置项
 */
public class Department extends ConfigItem {
    //所属site
    private Site site;
    //上级部门
    private Department parent;
    //下级部门
    private Set<Department> children;

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public Set<Department> getChildren() {
        return children;
    }

    public void setChildren(Set<Department> children) {
        this.children = children;
    }
}
