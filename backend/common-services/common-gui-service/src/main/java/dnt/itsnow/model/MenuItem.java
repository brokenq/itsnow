/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dnt.itsnow.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>菜单对象</h1>
 * <p/>
 * 不同的系统看到不同的菜单体系，不同的用户，根据其权限看到不同的菜单
 */
public class MenuItem extends Record {

    // 父菜单ID
    @JsonIgnore
    private Long parentId;
    @JsonIgnore
    // 父菜单
    private MenuItem parent;

    // 菜单名称
    @NotBlank
    private String name;
    // 菜单对应的angular state
    @NotBlank
    private String state;
    @NotBlank
    private String url;
    // 菜单对应的angular templateUrl
    @NotBlank
    private String templateUrl;
    // 菜单的样式
    private String css;
    // 菜单的描述
    private String description;
    // 菜单的展示顺序
    private Long position;
    // 菜单的快捷键
    private String shortcut;
    // 子菜单
    private List<MenuItem> children;

    public String getPath() {
        if (parent == null) {
            return "/" + getName();
        } else {
            return parent.getPath() + "/" + this.getName();
        }
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MenuItem getParent() {
        return parent;
    }

    public void setParent(MenuItem parent) {
        this.parent = parent;
        if( this.parent != null ){
            this.parent.addChild(this);
        }
    }

    public List<MenuItem> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }

    void addChild(MenuItem child){
        if(this.children == null ) this.children = new ArrayList<MenuItem>();
        if(!this.children.contains(child)) this.children.add(child);
    }

}
