/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;

import java.util.Set;

/**
 * <h1>MSU/MSP的部门</h1>
 */
public class Department extends Record {
    //所属site
    private Site site;
    //部门名称
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
