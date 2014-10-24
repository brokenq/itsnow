package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <h1>MSU/MSP的部门</h1>
 * 也是配置项
 */
public class Department extends ConfigItem implements Comparable<Department> {

    // 序列号
    private String sn;
    // 展示顺序
    private Long position;
    // 包含地点
    private List<Site> sites;
    // 包含员工
    private List<Staff> staffs;

    // 上级部门ID
    private Long parentId;
    // 上级部门
    @JsonIgnore
    private Department parent;
    // 下级部门
    private List<Department> children;

    public void setParent(Department parent) {
        this.parent = parent;
        if( this.parent != null ){
            this.parent.addChild(this);
        }
    }

    private void addChild(Department child) {
        if(this.children == null ) {
            this.children = new ArrayList<Department>();
        }
        if(!this.children.contains(child)){
            this.children.add(child);
        }
        Collections.sort(this.children);
    }

    @Override
    public int compareTo(Department another) {
        return (int)(this.position - another.position);
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Department getParent() {
        return parent;
    }

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Department{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", name=").append(getName());
        sb.append(", position=").append(position);
        sb.append(", description=").append(getDescription());
        sb.append('}');
        return sb.toString();
    }

}
