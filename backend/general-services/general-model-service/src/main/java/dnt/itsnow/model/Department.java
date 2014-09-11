package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;

/**
 * <h1>MSU/MSP的部门</h1>
 * 也是配置项
 */
public class Department extends ConfigItem {

    private String sn;
    //所属site
    private List<Site> sites;
    //上级部门
    @JsonIgnore
    private Department parent;
    //下级部门
    private Set<Department> children;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Department{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", sites=").append(sites);
        sb.append(", parent=").append(parent);
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }
}
