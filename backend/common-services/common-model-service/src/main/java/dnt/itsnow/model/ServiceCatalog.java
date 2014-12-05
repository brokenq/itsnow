/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import net.happyonroad.platform.model.Record;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <h1>服务目录</h1>
 *
 * 服务目录不作为配置项
 *
 */
public class ServiceCatalog extends Record{
    private Long parentId;
    @NotNull
    private String title;
    private String description;
    private String icon;

    @NotNull
    private String sn;
    private List<ServiceCatalog> children;
    @NotNull
    private Integer level;


    public List<ServiceCatalog> getChildren() {
        return children;
    }

    public void setChildren(List<ServiceCatalog> children) {
        this.children = children;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /*public List<ServiceItem> getItems() {
        return items;
    }

    public void setItems(List<ServiceItem> items) {
        this.items = items;
    }*/

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /*public ServiceItem getItemBySn(String sn) {
        if( items == null ) return null;
        for (ServiceItem item : items) {
            if( item.getSn().equals(sn) ) return item;
        }
        return null;
    }*/
}
