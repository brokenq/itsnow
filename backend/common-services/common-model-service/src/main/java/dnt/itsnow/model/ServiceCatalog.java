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
 * TODO 添加测试用例
 */
public class ServiceCatalog extends Record{
    private String title;
    private String description;
    private String icon;

    private List<ServiceItem> items;

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