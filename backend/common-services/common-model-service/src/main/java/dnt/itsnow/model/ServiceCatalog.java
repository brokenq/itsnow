/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;

import java.util.List;

/**
 * <h1>服务目录</h1>
 *
 * 服务目录不作为配置项
 *
 */
public class ServiceCatalog extends Record{
    private Long parentId;
    private String title;
    private String description;
    private String icon;
    private String sn;
    private List<ServiceCatalog> children;

    private List<ServiceItem> items;

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

    public List<ServiceItem> getItems() {
        return items;
    }

    public void setItems(List<ServiceItem> items) {
        this.items = items;
    }

    public ServiceItem getItemBySn(Long id) {
        if( items == null ) return null;
        for (ServiceItem item : items) {
            if( item.getId().equals(id) ) return item;
        }
        return null;
    }
}
