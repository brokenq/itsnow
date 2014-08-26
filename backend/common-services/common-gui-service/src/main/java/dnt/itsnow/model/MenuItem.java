/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>菜单对象</h1>
 *
 * 不同的系统看到不同的菜单体系，不同的用户，根据其权限看到不同的菜单
 */
public class MenuItem extends Record {

    // 父菜单ID
    private Long parentId;
    // 菜单名称
    @NotBlank
    private String name;
    // 菜单类型
    @NotBlank
    private String type;
    // 菜单对应的URL
    @NotBlank
    private String url;
    // 菜单的样式
    private String css;
    // 菜单的描述
    private String description;
    // 菜单的展示顺序
    private Long showOrder;
    // 菜单的快捷键
    private Long shortCut;

    private List<MenuItem> subMenuItems = new ArrayList<MenuItem>();

    public List<MenuItem> getSubMenuItems() {
        return subMenuItems;
    }

    public void setSubMenuItems(List<MenuItem> subMenuItems) {
        this.subMenuItems = subMenuItems;
    }

    public void addSubMenuItem(MenuItem subMenuItem) {
        subMenuItems.add(subMenuItem);
    }

    public Long getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Long showOrder) {
        this.showOrder = showOrder;
    }

    public Long getShortCut() {
        return shortCut;
    }

    public void setShortCut(Long shortCut) {
        this.shortCut = shortCut;
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
