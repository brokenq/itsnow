/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;

/**
 * <h1>菜单对象</h1>
 *
 * 不同的系统看到不同的菜单体系，不同的用户，根据其权限看到不同的菜单
 */
public class MenuItem extends Record {

    private Long parentId;

    private String name;

    private String type;

    private String url;

    private String css;

    private String description;

    private MenuItem subMenuItem;

    public MenuItem getSubMenuItem() {
        return subMenuItem;
    }

    public void setSubMenuItem(MenuItem subMenuItem) {
        this.subMenuItem = subMenuItem;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
